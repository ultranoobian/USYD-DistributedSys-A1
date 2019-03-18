import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class BlockchainClient {
    public static void main(String[] args) {

        if (args.length != 2) {
            return;
        }
        String serverName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        BlockchainClient bcc = new BlockchainClient();

        Socket sck;
        try {
            sck = new Socket(serverName, portNumber);

            bcc.clientHandler(sck.getInputStream(), sck.getOutputStream());
            sck.close();
        } catch (IOException ioe) {

        } finally {

        }
    }

    public void clientHandler(InputStream serverInputStream, OutputStream serverOutputStream) throws IOException {
        BufferedReader inputReader = new BufferedReader(
                new InputStreamReader(serverInputStream));
        PrintWriter outWriter = new PrintWriter(serverOutputStream, true);

        Scanner sc = new Scanner(System.in);
        int newLineCount = 0;
        char incomingChar;
        String stdLine;
        while (sc.hasNextLine()) {

            stdLine = sc.nextLine();
            outWriter.println(stdLine);
            outWriter.flush();

            if (stdLine.equals("cc")) {
                break;
            }

            while ((incomingChar = ((char) inputReader.read())) != -1) {
                if (incomingChar == '\n') {
                    newLineCount++;
                } else {
                    newLineCount = 0;
                }
                System.out.print(incomingChar);

                if (newLineCount == 2) break;
            }

        }

        sc.close();
        outWriter.close();
        inputReader.close();
    }

    // implement helper functions here if you need any.
}
