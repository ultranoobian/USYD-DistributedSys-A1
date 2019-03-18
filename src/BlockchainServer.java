import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.regex.Pattern;


public class BlockchainServer {
    private static boolean FLAG_DEBUG = false;
    private Blockchain blockchain;

    public BlockchainServer() {
        blockchain = new Blockchain();
    }

    // getters and setters
    public void setBlockchain(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public Blockchain getBlockchain() {
        return blockchain;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.print("Usage: BlockchainServer <port> [debug:true|false}");
            return;
        }

        int portNumber;
        try {
            portNumber = Integer.parseInt(args[0]);

        } catch (NumberFormatException nfe) {
            System.out.print("Error: Invalid port number!");
            return;
        }

        if (args.length == 2) {
            try {
                FLAG_DEBUG = Boolean.parseBoolean(args[1]);
            } catch (ArrayIndexOutOfBoundsException outOfBounds) {
                System.err.println(outOfBounds);
            }
        }

        BlockchainServer bcs = new BlockchainServer();
        if (FLAG_DEBUG) System.out.println(String.format("Attempting to start server with port %s", portNumber));

        ServerSocket sc;
        Socket sck;
        try {
            sc = new ServerSocket(portNumber);
            if (FLAG_DEBUG && sc.isBound()) System.out.println(String.format("Socket bound with port %s", portNumber));
            while (true) {
                if (FLAG_DEBUG) System.out.println("Waiting on Connection...");
                sck = sc.accept();
                if (FLAG_DEBUG) System.out.println("Client connected.");
                bcs.serverHandler(sck.getInputStream(), sck.getOutputStream());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serverHandler(InputStream clientInputStream, OutputStream clientOutputStream) throws IOException{

        BufferedReader inputReader = new BufferedReader(
                new InputStreamReader(clientInputStream));
        PrintWriter outWriter = new PrintWriter(clientOutputStream, false);

        try {
            String line;
            boolean exitSignalReceived = false;

            while (!exitSignalReceived) {
                if ((line = inputReader.readLine()) != null) {
                    // Do stuff when there is data
                    String[] lineComponents = line.split(Pattern.quote("|"));
                    if (lineComponents.length >= 1) {
                        switch (lineComponents[0]) {
                            case "tx":
                                // Add transaction (if valid).
                                switch (blockchain.addTransaction(line)) {
                                    case 0: //Failure
                                        if (FLAG_DEBUG) System.out.println(String.format("Unsuccessful TX: %s", line));
                                        outWriter.print("Rejected\n\n");
                                        outWriter.flush();
                                        break;
                                    case 1: // Success
                                        if (FLAG_DEBUG) System.out.println("Successful TX");
                                        outWriter.print("Accepted\n\n");
                                        outWriter.flush();
                                        break;
                                    case 2: // Success and new block formed
                                        if (FLAG_DEBUG) System.out.println("New block forged");
                                        outWriter.print("Accepted\n\n");
                                        outWriter.flush();
                                        break;
                                    default:
                                        break;
                                }

                                break;
                            case "pb":
                                // Print current blockchain.
                                outWriter.print(blockchain.toString() + "\n");
                                outWriter.flush();
                                break;
                            case "cc":
                                // Close connection
                                if (FLAG_DEBUG) System.out.println("Client closed connection");
                                exitSignalReceived = true;
                                break;
                            default:
                                if (FLAG_DEBUG) System.out.println(String.format("Unknown Command %s", line));
                                outWriter.print("Error\n\n");
                                outWriter.flush();
                                break;
                        }
                    }
                } else {
                    if (FLAG_DEBUG) System.out.println("Null received");
                    break;
                }
            }
        }
        catch(SocketException se){
            System.err.println("Socket Exception: Connection was reset.");
        }
        finally {
            inputReader.close();
            outWriter.close();
        }

    }
    // implement helper functions here if you need any.
}