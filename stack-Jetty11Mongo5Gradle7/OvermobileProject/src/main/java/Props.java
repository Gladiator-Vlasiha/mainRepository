import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum Props {
    INSTANCE;
    private static final String PROPS_PATH = "src/main/resources/app.properties";
    public final String username;
    public final String password;
    public final String host;
    public final String port;
    public final String params;
    public final String dbName;
    public final String collectionPayments;
    public final String id;
    public final String userId;
    public final String sum;
    public final String time;

    Props() {
        Properties props = new Properties();

        try (InputStream in = new FileInputStream(PROPS_PATH)) {
            props.load(in);
            System.out.println("* Settings received successfully");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("* Error reading the configuration file");
        }
        //читаем файл конфигурации
        this.username = props.getProperty("mongo.username");
        this.password = props.getProperty("mongo.password");
        this.host = props.getProperty("mongo.host");
        this.port = props.getProperty("mongo.port");
        this.params = props.getProperty("mongo.paramsAfterSlash");
        this.dbName = props.getProperty("mongo.database");
        this.collectionPayments = props.getProperty("mongo.collectionPayments");
        this.id = props.getProperty("mongo.collectionPayments.id");
        this.userId = props.getProperty("mongo.collectionPayments.userId");
        this.sum = props.getProperty("mongo.collectionPayments.sum");
        this.time = props.getProperty("mongo.collectionPayments.time");

    }

    public String getMongoURI() {//подключаем к базе через app.properties
        String credentials = (username.isBlank() || password.isBlank()) ?
                "" : String.format("%s:%s@", this.username, this.password);
        String portValue = this.port.isBlank() ?
                "" : String.format(":%s", this.port);
        String paramsValue = this.params.isBlank() ?
                "" : String.format("/%s", this.params);
        return String.format("mongodb://%s%s%s%s", credentials, host, portValue, paramsValue)
                .replaceAll("\\s+", "");
    }

}
