import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            // Connect to RMI registry on localhost:1099
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Look up the remote object
            hello stub = (hello) registry.lookup("HelloService");

            // Call remote methods
            System.out.println(stub.sayHello("Khushbu"));
            System.out.println("Sum of 10 + 20 = " + stub.add(10, 20));

        } catch (Exception e) {
            System.out.println("Client exception: " + e);
        }
    }
}