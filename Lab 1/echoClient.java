import java.io.*;
import java.net.*;
import java.util.Scanner;

public class echoClient {
    private static final String SERVER = "localhost";
    private static final int PORT = 8088;
    
    public static void main(String[] args) {
        System.out.println("Name: Khushbu Jain");
        System.out.println("Roll Number: 23115047");
        System.out.println("Connecting to server...");

        try {
            Socket socket = new Socket(SERVER, PORT);
            BufferedReader serverReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            PrintWriter serverWriter = new PrintWriter(
                socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            System.out.println("Connected!");
            
            // Read welcome messages from server
            System.out.println(serverReader.readLine());
            System.out.println(serverReader.readLine());

            // Thread to receive messages from server
            Thread receiver = new Thread(() -> {
                try {
                    String response;
                    while ((response = serverReader.readLine()) != null) {
                        System.out.println(response);
                    }
                } catch (IOException e) {
                    System.out.println("Connection closed by server");
                }
            });
            receiver.setDaemon(true);
            receiver.start();

            // Send messages to server
            while (true) {
                System.out.print("> ");
                String msg = scanner.nextLine();
                serverWriter.println(msg);
                if (msg.equalsIgnoreCase("exit")) {
                    break;
                }
            }
            
            socket.close();
            scanner.close();

        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
            System.err.println("Make sure the server is running on port " + PORT);
        }
        System.out.println("Client disconnected.");
    }
}