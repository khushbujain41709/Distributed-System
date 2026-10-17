import java.util.concurrent.atomic.AtomicInteger;
public class SharedCounterIncrement {
    // Shared counter (shared memory) accessed by all threads
    private static final AtomicInteger counter = new AtomicInteger(0);

    // Number of threads and increments per thread
    private static final int NUM_THREADS = 5;
    private static final int INCREMENTS_PER_THREAD = 1000;

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Starting counter increment with "
                + NUM_THREADS + " threads, "
                + INCREMENTS_PER_THREAD + " increments each.");
        System.out.println("Expected final value: "
                + (NUM_THREADS * INCREMENTS_PER_THREAD));
        System.out.println("---------------------------------------------");

        // Create an array to hold thread references
        Thread[] workers = new Thread[NUM_THREADS];

        // Create and start each thread
        for (int i = 0; i < NUM_THREADS; i++) {
            final int threadId = i + 1;

            workers[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                    // Atomically increment the shared counter
                    counter.incrementAndGet();
                }
                System.out.println("Thread " + threadId + " finished.");
            }, "Worker-" + threadId);

            workers[i].start();
        }

        // Wait for all threads to finish (join)
        for (int i = 0; i < NUM_THREADS; i++) {
            workers[i].join();
        }

        // Print the final value
        System.out.println("---------------------------------------------");
        System.out.println("Final Counter Value: " + counter.get());
        System.out.println("Expected Value   : " + (NUM_THREADS * INCREMENTS_PER_THREAD));

        if (counter.get() == NUM_THREADS * INCREMENTS_PER_THREAD) {
            System.out.println("SUCCESS: No race condition occurred.");
        } else {
            System.out.println("FAILURE: Race condition detected!");
        }
    }
}