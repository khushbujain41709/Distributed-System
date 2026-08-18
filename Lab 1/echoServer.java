import java.io.*;
import java.net.*;

public class echoServer {
    private static final int PORT = 8088;
    
    public static void main(String[] args) {
        System.out.println("Server started on port: " + PORT);
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            int clientId = 0;
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientId++;
                System.out.println("Client #" + clientId + " connected");
                
                ClientHandler handler = new ClientHandler(clientSocket, clientId);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;
    private int clientId;
    
    public ClientHandler(Socket socket, int id) {
        this.socket = socket;
        this.clientId = id;
    }
    
    @Override
    public void run() {
        try (
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(
                socket.getOutputStream(), true)
        ) {
            writer.println("Welcome! You are Client #" + clientId);
            writer.println("Type 'exit' to disconnect");
            
            String message;
            while ((message = reader.readLine()) != null) {
                if (message.equalsIgnoreCase("exit")) {
                    writer.println("Goodbye!");
                    break;
                }
                writer.println("Echo: " + message);
                System.out.println("Client #" + clientId + ": " + message);
            }
        } catch (IOException e) {
            System.err.println("Client #" + clientId + " error");
        } finally {
            try { socket.close(); } catch (IOException e) {}
            System.out.println("Client #" + clientId + " disconnected");
        }
    }
}