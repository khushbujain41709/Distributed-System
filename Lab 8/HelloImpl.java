import HelloApp.*;
import org.omg.CORBA.*;

public class HelloImpl extends HelloPOA {
    public String sayHello() {
        return "Hello from CORBA Server!";
    }
}