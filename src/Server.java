import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
      /*  if (args.length != 1) {
            System.err.println("Usage: java KKMultiServer <port number>");
            System.exit(1);
        }
*/      System.out.println("Server is running...");
        int portNumber = 5000;//Integer.parseInt(args[0]);
        boolean listening = true;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (listening) {
                new ServerThread(serverSocket.accept()).start();// This will listen to connections
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}