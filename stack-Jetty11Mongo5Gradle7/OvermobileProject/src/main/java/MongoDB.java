import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import org.apache.log4j.Logger;
import org.bson.Document;

public class MongoDB implements AutoCloseable {
    //конфиг
    private static final Props PROPS = Props.INSTANCE;
    private static MongoDB instance;
    private final MongoClient mongoClient;
    private final MongoDatabase db;
    private final MongoCollection<Document> collectionPayments;
    private static Logger logger = Logger.getRootLogger();

    public static MongoDB getInstance() {
        if (instance == null) {
            instance = new MongoDB();
        }
        return instance;
    }

    private MongoDB() {//собираем конфиг
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(PROPS.getMongoURI()))
                .retryWrites(true)
                .build();
        mongoClient = MongoClients.create(settings);
        db = mongoClient.getDatabase(PROPS.dbName);//получаем бд от mongo
        dropCollections(db);
        collectionPayments = db.getCollection(PROPS.collectionPayments);
        //createSamePayments();//для теста пожно добавить вручную в базу
    }

    public void createSamePayments() {
        logger.info("создали  тестовую запись" + collectionPayments.countDocuments());
        collectionPayments.insertOne(new Document("id", 1).append("userId", 1).append("sum", 1).append("time", System.currentTimeMillis()));


    }

    private void dropCollections(MongoDatabase db) {//чистим бд
        db.getCollection(PROPS.collectionPayments).drop();
    }


    @Override
    public void close() throws Exception {//закрываем сессию
        dropCollections(db);
        mongoClient.close();
    }

    public MongoCollection<Document> getCollectionPayments() {
        return collectionPayments;
    }
}
