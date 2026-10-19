import HelloApp.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;

public class Client {
    public static void main(String args[]) {
        try {
            ORB orb = ORB.init(args, null);

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            Hello hello = HelloHelper.narrow(ncRef.resolve_str("Hello"));

            System.out.println(hello.sayHello());
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }
    }
}