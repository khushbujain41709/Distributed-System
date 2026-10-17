import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

// ---------------- Coordinator (Server) ----------------
class Coordinator {
    private final AtomicBoolean busy = new AtomicBoolean(false);
    private final Queue<Integer> requestQueue = new LinkedList<>();

    // Process requests permission
    public synchronized void request(int processId) {
        System.out.println("  [Coordinator] Request received from P" + processId);

        if (busy.compareAndSet(false, true)) {
            // Resource free -> grant immediately
            System.out.println("  [Coordinator] GRANTED to P" + processId);
        } else {
            // Resource busy -> queue the request
            requestQueue.add(processId);
            System.out.println("  [Coordinator] P" + processId + " QUEUED (resource busy)");
        }
    }

    // Process releases the resource
    public synchronized void release(int processId) {
        System.out.println("  [Coordinator] RELEASE from P" + processId);

        if (!requestQueue.isEmpty()) {
            int next = requestQueue.poll();
            System.out.println("  [Coordinator] GRANTED to P" + next + " (from queue)");
        } else {
            busy.set(false);
            System.out.println("  [Coordinator] Resource is now FREE");
        }
    }
}

// ---------------- Process (Client) ----------------
class Process extends Thread {
    private final int id;
    private final Coordinator coordinator;

    public Process(int id, Coordinator coordinator) {
        this.id = id;
        this.coordinator = coordinator;
        setName("P" + id);
    }

    @Override
    public void run() {
        // Enter critical section
        System.out.println("P" + id + " wants to ENTER critical section");
        coordinator.request(id);

        // Simulate critical section work
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        System.out.println("P" + id + " EXITED critical section");
        coordinator.release(id);
    }
}

// ---------------- Main ----------------
public class DistributedMutualExclusion {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Distributed Mutual Exclusion (Centralized) ===\n");

        Coordinator coordinator = new Coordinator();

        // Create 3 processes
        Process p1 = new Process(1, coordinator);
        Process p2 = new Process(2, coordinator);
        Process p3 = new Process(3, coordinator);

        // Start them (almost simultaneously)
        p1.start();
        Thread.sleep(50);   // small delay so order is visible
        p2.start();
        Thread.sleep(50);
        p3.start();

        // Wait for all to finish
        p1.join();
        p2.join();
        p3.join();

        System.out.println("\n=== All processes finished ===");
    }
}