import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Pattern;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;


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
                printStackTrace();
                System.err.println(outOfBounds);
            }
        }

        BlockchainServer bcs = new BlockchainServer();
        if (FLAG_DEBUG) System.out.println(String.format("Attempting to start server with port %s", portNumber));

        try {
            ServerSocket sc = new ServerSocket(portNumber);
            while (true) {
                handleSocketAcceptation(sc.accept(), bcs.getBlockchain());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleSocketAcceptation(Socket sck, Blockchain blockchain) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(sck.getInputStream()));
        PrintWriter printWriter = new PrintWriter(sck.getOutputStream());
        try{
            String line;
            boolean exitSignalReceived = false;

            while (!exitSignalReceived) {
                if ((line = bufferedReader.readLine()) != null) {
                    // Do stuff when there is data
                    String[] lineComponents = line.split(Pattern.quote("|"));
                    if (lineComponents.length >= 1) {
                        switch (lineComponents[0]) {
                            case "tx":
                                // Add transaction (if valid).
                                switch(blockchain.addTransaction(line)) {
                                    case 0: //Failure
                                        printWriter.print("Rejected\n\n");
                                    case 1: // Success
                                    case 2: // Success and new block formed
                                        printWriter.print("Accepted\n\n");
                                    default:
                                        break;
                                }

                                break;
                            case "pb":
                                // Print current blockchain.
                                printWriter.print(blockchain.toString());
                                break;
                            case "cc":
                                // Close connection
                                exitSignalReceived = true;
                                break;
                            default:
                                printWriter.print("Error\n\n");
                                break;
                        }
                    }
                }
            }
        }
        finally {
            bufferedReader.close();
            printWriter.close();
            sck.close();
        }


    }

    // implement helper functions here if you need any.
}