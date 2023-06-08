package Monitor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class RecursMonitor {
    public int numA = 0;
    public int numB = 0;
    public Lock lock = new ReentrantLock();
    public Condition condition = lock.newCondition();

    public void a_puc_utilitzar() throws InterruptedException {
        lock.lock();
        try {
            while (numB < 2 * numA) {
                condition.await();
            }
        } finally {
            lock.unlock();
        }
    }

    public void a_entra() {
        lock.lock();
        try {
            numA++;
        } finally {
            lock.unlock();
        }
    }

    public void a_surto() {
        lock.lock();
        try {
            numA--;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void b_entra() {
        lock.lock();
        try {
            numB++;
        } finally {
            lock.unlock();
        }
    }

    public void b_surto() {
        lock.lock();
        try {
            numB--;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

class ProcessA extends Thread {
    public RecursMonitor monitor;

    public ProcessA(RecursMonitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        try {
            monitor.a_entra();
            monitor.a_puc_utilitzar();
            // Realiza las acciones necesarias cuando el proceso A utiliza el recurso
            System.out.println("Proceso A utilizando el recurso");
            monitor.a_surto();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ProcessB extends Thread {
    public RecursMonitor monitor;

    public ProcessB(RecursMonitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        monitor.b_entra();
        // Realiza las acciones necesarias cuando el proceso B utiliza el recurso
        System.out.println("Proceso B utilizando el recurso");
        monitor.b_surto();
    }
}
