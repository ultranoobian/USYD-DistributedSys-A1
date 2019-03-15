import java.util.regex.*;

public class Transaction {
    private String sender;
    private String content;
    private boolean valid = false;

    // getters and setters
    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public String toString() {
        return String.format("|%s|%70s|\n", sender, content);
    }

    public String toHashString() {
        return String.format("tx|%s|%s", this.getSender(), this.getContent());
    }

    public static boolean validateContent(String content) {
        return ((content.length() >= 70) && (content.indexOf('|') == -1));
    }

    public static boolean validateSender(String sender) {
        Pattern unikeyPattern = Pattern.compile("[a-z]{4}[0-9]{4}");
        Matcher unikeyMatcher = unikeyPattern.matcher(sender);

        return unikeyMatcher.matches();
    }

    public boolean validateContent() {
        return Transaction.validateContent(this.content);
    }

    public boolean validateSender() {
        return Transaction.validateSender(this.sender);
    }

}
