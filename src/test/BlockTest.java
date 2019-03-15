import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Base64;

import static org.junit.Assert.*;

public class BlockTest {

    @Test
    public void getPreviousBlock_None() {
        Block blk = new Block();
        assertNull(blk.getPreviousBlock());
    }

    @Test
    public void getPreviousBlock_NewBlock() {
        Block blk = new Block();
        Block blk2 = new Block();
        blk.setPreviousBlock(blk2);
        assertEquals(blk2, blk.getPreviousBlock());
    }

    @Test
    public void getPreviousBlock_SelfReference() {
        Block blk = new Block();
        blk.setPreviousBlock(blk);
        assertEquals(blk, blk.getPreviousBlock());
    }

    @Test
    public void getPreviousHash() {
        //todo
    }

    @Test
    public void getTransactions() {
        //todo
    }

    @Test
    public void setPreviousBlock() {
        //todo
    }

    @Test
    public void setPreviousHash() {
        //todo
    }

    @Test
    public void setTransactions() {
        //todo
    }

    @Test
    public void BlockToString() {
    }

    @Test
    public void calculateHash() {
        //todo
    }

    @Test
    public void calculateHash_Empty() {
        Block blk = new Block();
        Transaction t1 = new Transaction();
        Transaction t2 = new Transaction();
        Transaction t3 = new Transaction();

        t1.setSender("test0000"); t1.setContent("1");
        t2.setSender("test0000"); t2.setContent("2");
        t3.setSender("test0000"); t3.setContent("3");
        ArrayList<Transaction> transacts = new ArrayList<Transaction>();
        transacts.add(t1);
        transacts.add(t2);
        transacts.add(t3);
        blk.setTransactions(transacts);

        byte[] hash = blk.calculateHash();
        assertEquals("jWuaYc5TOawKJew+B+tYuLZT0NDsTo6NDKEJdmgJyBk=", Base64.getEncoder().encodeToString(hash));
    }
}