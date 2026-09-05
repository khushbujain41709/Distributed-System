import java.io.*;
import java.net.*;
import java.util.Date;

public class SocketOptionsServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server started on port 5000");
            
            // Configure socket options on ServerSocket
            serverSocket.setReceiveBufferSize(8192);
            System.out.println("SO_RCVBUF set to: " + serverSocket.getReceiveBufferSize());
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("\nClient connected: " + clientSocket.getInetAddress());
                
                // Configure options on client socket
                clientSocket.setKeepAlive(true);
                clientSocket.setSoLinger(true, 5);
                clientSocket.setSendBufferSize(16384);
                clientSocket.setReceiveBufferSize(16384);
                clientSocket.setTcpNoDelay(true);
                
                // Display configured options
                System.out.println("SO_KEEPALIVE: " + clientSocket.getKeepAlive());
                System.out.println("SO_LINGER: " + clientSocket.getSoLinger());
                System.out.println("SO_SNDBUF: " + clientSocket.getSendBufferSize());
                System.out.println("SO_RCVBUF: " + clientSocket.getReceiveBufferSize());
                System.out.println("TCP_NODELAY: " + clientSocket.getTcpNoDelay());
                
                // Send response
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("Server Date & Time: " + new Date().toString());
                out.println("Socket Options: KEEPALIVE=true, LINGER=5s, SNDBUF=16KB, RCVBUF=16KB, NODELAY=true");
                
                out.close();
                clientSocket.close();
            }
            
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}