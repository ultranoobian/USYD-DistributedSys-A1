import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.UTFDataFormatException;
import java.util.ArrayList;
import java.util.Base64;

import static junit.framework.TestCase.*;

public class BlockchainTest {
    Blockchain mainChain;


    @Before
    public void setUp() throws Exception {
        mainChain = new Blockchain();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addTransactionZero() {
        assertNotNull(mainChain);
        assertNull(mainChain.getHead());
        assertEquals(0, mainChain.getPool().size());
        assertEquals(0, mainChain.getLength());
    }

    @Test
    public void addTransactionOne() {
        assertNotNull(mainChain);
        assertNull(mainChain.getHead());
        assertNotNull(mainChain.getPool());

        mainChain.addTransaction("tx|test0000|1");


        assertEquals(1, mainChain.getPool().size());
        assertEquals(0, mainChain.getLength());
    }

    @Test
    public void addTransactionThree() {
        assertNotNull(mainChain);
        assertNull(mainChain.getHead());
        assertNotNull(mainChain.getPool());

        ArrayList<Transaction> blk = mainChain.getPool();

        mainChain.addTransaction("tx|test0000|1");
        mainChain.addTransaction("tx|test0000|2");
        mainChain.addTransaction("tx|test0000|3");

        assertNotSame(mainChain.getPool(), blk);
        assertEquals(0, mainChain.getPool().size());
        assertEquals(1, mainChain.getLength());
    }
    @Test
    public void addTransactionFour() {
        assertNotNull(mainChain);
        assertNull(mainChain.getHead());
        assertNotNull(mainChain.getPool());

        ArrayList<Transaction> blk = mainChain.getPool();

        mainChain.addTransaction("tx|test0000|1");
        mainChain.addTransaction("tx|test0000|2");
        mainChain.addTransaction("tx|test0000|3");
        mainChain.addTransaction("tx|test0000|4");

        assertNotSame(mainChain.getPool(), blk);
        assertEquals(1, mainChain.getPool().size());
        assertEquals(1, mainChain.getLength());
    }
    @Test
    public void addTransactionMultiple() {
        assertNotNull(mainChain);
        assertNull(mainChain.getHead());
        assertNotNull(mainChain.getPool());

        ArrayList<Transaction> pool = mainChain.getPool();

        mainChain.addTransaction("tx|test0000|1");
        mainChain.addTransaction("tx|test0000|2");
        mainChain.addTransaction("tx|test0000|3");
        assertNotSame(mainChain.getPool(), pool);
        pool = mainChain.getPool();
        mainChain.addTransaction("tx|test0000|4");
        mainChain.addTransaction("tx|test0000|5");
        mainChain.addTransaction("tx|test0000|6");
        assertNotSame(mainChain.getPool(), pool);

        assertEquals("Lakir/jIQFUGnf+UUnRbiuYsNDOcGXekM+2cKXVmyRw=", Base64.getEncoder().encodeToString(mainChain.getHead().calculateHash()));
        assertEquals(0, mainChain.getPool().size());
        assertEquals(2, mainChain.getLength());
    }

}