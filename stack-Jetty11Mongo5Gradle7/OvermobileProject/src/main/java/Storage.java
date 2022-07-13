import com.mongodb.client.MongoCollection;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.bson.BsonDocument;
import org.bson.Document;

import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class Storage implements AutoCloseable {
    private static final MongoDB DB = MongoDB.getInstance();
    private static final Props PROPS = Props.INSTANCE;
    private static final String PROPS_PATH = "src/main/resources/app.properties";
    private static final String EXIT_MSG = "* Closed the session with the database";
    public static final String ADD_PAY = "ADD_PAY";
    private static final String INVALID_COMMAND = "* Invalid command or syntax";
    private static final String CHECK_ID = "The entry is already in the database with this id ";
    private static final String BE_ALREADY = "The record already exists in the database with such id and userId";
    private static final String ADD_NEW = "* There is no such payment in the database, we create a new record\"";
    private static final String RE_ADD_NEW = "* Different userId record the payment with a new id";
    private static final String MISS_PAY = "* The id and userId match, we do nothing";

    private static Logger logger = Logger.getRootLogger();

    private final MongoCollection<Document> collectionPayments;

    public Storage() {
        this.collectionPayments = DB.getCollectionPayments();
    }

    public void perform(String inputString) throws Exception {//выполняем команду полученную в консоли
        String[] strings = inputString.split(" ");//корректность команды
        if ((strings.length == 4) & (strings[0].equalsIgnoreCase(ADD_PAY))) {
            int id = Integer.parseInt(strings[1]);
            int userId = Integer.parseInt(strings[2]);
            int sum = Integer.parseInt(strings[3]);
            long time = System.currentTimeMillis();
            addPay(id, userId, sum, time);
        } else {
            System.out.println(INVALID_COMMAND);
        }
    }

    public int addPay(int id, int userId, int sum, long time) {//добавляем платеж в бд
        String log4jConfPath = PROPS_PATH;
        PropertyConfigurator.configure(log4jConfPath);

        logger.info(CHECK_ID);
        if (getPayById(id).isEmpty()) {//если нету такого
            logger.info(ADD_NEW);
            return createPay(userId, sum, time, id);
        }//если такой id есть проверяем совподает ли пользователь
        else if (getUserIdByID(id, userId)) {//eсли id одинаковые но user разный
            logger.info(RE_ADD_NEW);
            //BsonDocument queryLast = BsonDocument.parse("{ id : " + -1 + " }");
            BsonDocument queryLast = BsonDocument.parse("{ id : -1  }");
            int lastId = collectionPayments.find().sort(queryLast).first().getInteger("id");
            logger.info("* last id =" + lastId);
            int newId = lastId + 1;
            return createPay(userId, sum, time, newId);
        } else {//если id  и user совподают ADD_PAY 1 100 100
            logger.info(MISS_PAY);
            System.out.println(BE_ALREADY + " - " + id + " - " + userId);
            return 1;
        }
    }

    private int createPay(int userId, int sum, long time, int newId) {
        Document pay = new Document()
                .append(PROPS.id, newId)
                .append(PROPS.userId, userId)
                .append(PROPS.sum, sum)
                .append(PROPS.time, time);
        collectionPayments.insertOne(pay);//добавляем в бд
        logger.info("* Pay  id \"" + newId + ", userId " + userId + ", sum " + sum + "\", add in DB.");
        return 0;
    }

    private Optional<Document> getPayById(int id) {//ищем в БД платеж по id
        return Optional.ofNullable(collectionPayments.find(eq(PROPS.id, id)).first());
    }

    private boolean getUserIdByID(int id, int userId) {//ищем в БД платеж по id
        Document founded = collectionPayments.find(new Document("id", id)).first();
        founded.getInteger("userId");
        if ((founded.getInteger("userId") != userId)) {//
            return true;
        }
        return false;
    }


    @Override
    public void close() throws Exception {//закрываем сессию
        DB.close();
        System.out.println(EXIT_MSG);
    }

    public MongoCollection<Document> getCollectionPayments() {
        return collectionPayments;
    }
}

