import java.io.*;
import java.net.*;

public class dayTimeClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            System.out.println("Connected to server");
            System.out.println("Server says: " + reader.readLine());
            
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }
}