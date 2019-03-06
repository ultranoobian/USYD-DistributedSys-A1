import java.util.ArrayList;

public class Blockchain {
    private Block head = null;
    private ArrayList<Transaction> pool;
    private int length;

    private final int poolLimit = 3;

    public Blockchain() {
        pool = new ArrayList<>();
        length = 0;
    }

    // getters and setters
    public Block getHead() {
        return head;
    }

    public ArrayList<Transaction> getPool() {
        return pool;
    }

    public int getLength() {
        return length;
    }

    public void setHead(Block head) {
        this.head = head;
    }

    public void setPool(ArrayList<Transaction> pool) {
        this.pool = pool;
    }

    public void setLength(int length) {
        this.length = length;
    }

    // add a transaction
    public int addTransaction(String txString) {
        Transaction t = new Transaction();
        // Construct transaction object
        String[] st = txString.split("|");
        if (st[0].equals("tx") && st.length == 3) {
            t.setSender(st[1]);
            t.setContent(st[2]);
        }

        //Validate and add transaction
        if (t.validateSender() && t.validateContent()) {
            pool.add(t);

            // Check if pool limit reached
            if (pool.size() >= poolLimit) {
                // Create and add new block
                Block block = new Block();

                if (head == null) {
                    block.setPreviousBlock(null);
                    block.setPreviousHash(new byte[32]);
                } else {
                    block.setPreviousBlock(head);
                    block.setPreviousHash(head.calculateHash());
                }
                block.setTransactions(getPool());

                this.setHead(block);
                this.setLength(this.getLength() + 1);
                return 2;
            }
        } else {
            return 0;
        }

        return 1;
    }

    public String toString() {
        String cutOffRule = new String(new char[81]).replace("\0", "-") + "\n";
        String poolString = "";
        for (Transaction tx : pool) {
            poolString += tx.toString();
        }

        String blockString = "";
        Block bl = head;
        while (bl != null) {
            blockString += bl.toString();
            bl = bl.getPreviousBlock();
        }

        return "Pool:\n"
                + cutOffRule
                + poolString
                + cutOffRule
                + blockString;
    }

    // implement helper functions here if you need any.
}