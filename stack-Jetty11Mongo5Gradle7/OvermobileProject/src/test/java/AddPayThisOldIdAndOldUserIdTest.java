import org.junit.Assert;
import org.junit.Test;


public class AddPayThisOldIdAndOldUserIdTest {
    @Test
    public void addPayThisOldIdAndOldUserIdTest() throws InterruptedException {
        Thread.sleep(3000);
        Storage storage = new Storage();
        storage.addPay(2, 2, 2, 2);
        int status = storage.addPay(2, 2, 2, 2);
        Assert.assertEquals(1, status);
    }
}
