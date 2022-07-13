import org.junit.Assert;
import org.junit.Test;


public class AddPayThisOldIdAndNewUserIdTest {
    @Test
    public void addPayThisOldIdAndNewUserIdTest() throws InterruptedException {
        Thread.sleep(3000);
        Storage storage = new Storage();
        storage.addPay(2, 2, 2, 2);
        int status = storage.addPay(2, 3, 2, 2);
        Assert.assertEquals(0, status);
    }
}
