import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            // Create the remote object
            helloImpl obj = new helloImpl();

            // Start RMI registry on port 1099
            Registry registry = LocateRegistry.createRegistry(1099);

            // Bind the object with a name
            registry.rebind("HelloService", obj);

            System.out.println("Server is ready and waiting...");
        } catch (Exception e) {
            System.out.println("Server exception: " + e);
        }
    }
}