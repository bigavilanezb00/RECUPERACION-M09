package Monitor;

public class Main {
    public static void main(String[] args) {
        RecursMonitor monitor = new RecursMonitor();

        // Crear y ejecutar los procesos A y B
        ProcessA procesoA = new ProcessA(monitor);
        ProcessB procesoB = new ProcessB(monitor);

        procesoA.start();
        procesoB.start();

        try {
            procesoA.join();
            procesoB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
