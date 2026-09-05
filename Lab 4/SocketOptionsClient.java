import java.io.*;
import java.net.*;

public class SocketOptionsClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            System.out.println("Connected to server");
            System.out.println("Local Port: " + socket.getLocalPort());
            
            // Display client socket options
            System.out.println("\nClient Socket Options:");
            System.out.println("SO_KEEPALIVE: " + socket.getKeepAlive());
            System.out.println("SO_LINGER: " + socket.getSoLinger());
            System.out.println("SO_SNDBUF: " + socket.getSendBufferSize());
            System.out.println("SO_RCVBUF: " + socket.getReceiveBufferSize());
            System.out.println("TCP_NODELAY: " + socket.getTcpNoDelay());
            
            // Read and display server response
            System.out.println("\nServer Responses:");
            String response;
            while ((response = reader.readLine()) != null) {
                System.out.println(response);
            }
            
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }
}