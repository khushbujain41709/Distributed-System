import java.io.*;
import java.net.*;
import java.util.Date;

public class dayTimeServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server started on port 5000");
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from: " + clientSocket.getInetAddress());
                
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("Server Date & Time: " + new Date().toString());
                
                out.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}