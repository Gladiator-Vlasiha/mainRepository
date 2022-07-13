import org.junit.Assert;
import org.junit.Test;


public class AddOneNewPayTest {

    @Test
    public void addOneNewPayTest() throws InterruptedException {
        Thread.sleep(3000);
        Storage storage = new Storage();
        int status = storage.addPay(2, 2, 2, 2);
        Assert.assertEquals(0, status);
    }
}
