import java.io.*;
import java.net.*;
import java.util.Scanner;

public class chatClient {
    
    public static void main(String[] args) {
        System.out.println("Connecting to chat server...");
        
        try (Socket socket = new Socket("localhost", 8088);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {
            
            System.out.println("Connected to server!");
            
            // Thread to receive messages
            Thread receiver = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = reader.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server");
                }
            });
            receiver.setDaemon(true);
            receiver.start();
            
            // Send messages
            while (true) {
                String msg = scanner.nextLine();
                writer.println(msg);
                if (msg.equalsIgnoreCase("exit")) {
                    break;
                }
            }
            
        } catch (IOException e) {
            System.out.println("Cannot connect to server. Make sure server is running.");
        }
        System.out.println("Client disconnected.");
    }
}