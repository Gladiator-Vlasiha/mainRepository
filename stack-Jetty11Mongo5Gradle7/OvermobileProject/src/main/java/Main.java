
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    private static final String HELLO = "* Enter the command: ... ";
    private static final String EXIT = "EXIT";

    public static void main(String[] args) throws InterruptedException, Exception {

        System.out.println("Hello");
        try (BufferedReader readConsole = new BufferedReader(new InputStreamReader(System.in));
             Storage store = new Storage()) {
            System.out.println("* Connection to MongoDB-successful");
            while (true) {
                Thread.sleep(500);
                System.out.print(HELLO + "\n");
                String command = readConsole.readLine().trim();//обрабатываем ввод
                if (command.equalsIgnoreCase(EXIT)) {
                    break;
                } else {
                    store.perform(command);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("* Connection to MongoDB-failed");
        }
    }
}
