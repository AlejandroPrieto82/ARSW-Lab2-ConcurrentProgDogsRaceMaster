package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread {

    private volatile boolean running = true;
    private int a, b;
    private List<Integer> primes = new LinkedList<>();
    private int lastNumberChecked;

    public PrimeFinderThread(int a, int b) {
        super();
        this.a = a;
        this.b = b;
        this.lastNumberChecked = a - 1; // Inicializamos antes del primer número
    }

    public void stopRunning() {
        running = false;
    }

    @Override
    public void run() {
        for (int i = a; i <= b && running; i++) {
            lastNumberChecked = i; // Guardamos el último número evaluado
            if (isPrime(i)) {
                primes.add(i);
                System.out.println(i);
            }
        }
    }

    boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    public List<Integer> getPrimes() {
        return primes;
    }

    public int getLastNumberChecked() {
        return lastNumberChecked;
    }
}
