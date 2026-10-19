import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class helloImpl extends UnicastRemoteObject implements hello {

    public helloImpl() throws RemoteException {
        super();
    }

    public String sayHello(String name) throws RemoteException {
        return "Hello, " + name + "! Welcome to Java RMI.";
    }

    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}