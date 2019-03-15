import org.junit.Test;

import static org.junit.Assert.*;

public class TransactionTest {

    @Test
    public void toHashString() {
        Transaction transaction = new Transaction();
        transaction.setSender("abcd0000");
        transaction.setContent("GigglyBoo");
        assertEquals("tx|abcd0000|GigglyBoo", transaction.toHashString());
    }
}