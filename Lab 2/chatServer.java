import java.io.*;
import java.net.*;
import java.util.*;

public class chatServer {
    
    private static List<ClientThread> clients = new ArrayList<>();
    private static int clientId = 0;
    
    public static void main(String[] args) {
        System.out.println("Chat Server started on port 8088");
        
        try (ServerSocket serverSocket = new ServerSocket(8088)) {
            while (true) {
                Socket socket = serverSocket.accept();
                clientId++;
                System.out.println("Client " + clientId + " connected");
                
                ClientThread client = new ClientThread(socket, clientId);
                clients.add(client);
                new Thread(client).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
    
    // Send message to all clients
    public static void broadcast(String message, ClientThread sender) {
        for (ClientThread client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }
    
    // Remove client from list
    public static void removeClient(ClientThread client) {
        clients.remove(client);
        System.out.println("Client " + client.id + " left");
        broadcast("User " + client.id + " left the chat", null);
    }
}

class ClientThread implements Runnable {
    Socket socket;
    int id;
    PrintWriter writer;
    BufferedReader reader;
    
    public ClientThread(Socket socket, int id) {
        this.socket = socket;
        this.id = id;
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Error creating streams");
        }
    }
    
    @Override
    public void run() {
        try {
            sendMessage("Welcome! You are User " + id);
            sendMessage("Type 'exit' to quit");
            chatServer.broadcast("User " + id + " joined the chat", this);
            
            String msg;
            while ((msg = reader.readLine()) != null) {
                if (msg.equalsIgnoreCase("exit")) {
                    break;
                }
                String formattedMsg = "User " + id + ": " + msg;
                System.out.println(formattedMsg);
                chatServer.broadcast(formattedMsg, this);
            }
            
            socket.close();
            chatServer.removeClient(this);
            
        } catch (IOException e) {
            System.out.println("Client " + id + " disconnected");
        }
    }
    
    public void sendMessage(String msg) {
        writer.println(msg);
    }
}