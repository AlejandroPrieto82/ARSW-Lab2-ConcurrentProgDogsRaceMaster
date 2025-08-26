package edu.eci.arsw.primefinder;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            PrimeFinderThread pft1 = new PrimeFinderThread(0, 10000000);
            PrimeFinderThread pft2 = new PrimeFinderThread(10000001, 20000000);
            PrimeFinderThread pft3 = new PrimeFinderThread(20000001, 30000000);

            pft1.start();
            pft2.start();
            pft3.start();

            Thread.sleep(5000);
			
            pft1.stopRunning();
            pft2.stopRunning();
            pft3.stopRunning();

            pft1.join();
            pft2.join();
            pft3.join();

            int totalPrimos = pft1.getPrimes().size() + pft2.getPrimes().size() + pft3.getPrimes().size();
            System.out.println("Primos encontrados hasta ahora: " + totalPrimos);

            System.out.println("Presiona ENTER para continuar...");
            new Scanner(System.in).nextLine();

            PrimeFinderThread pft1b = new PrimeFinderThread(pft1.getLastNumberChecked() + 1, 10000000);
            PrimeFinderThread pft2b = new PrimeFinderThread(pft2.getLastNumberChecked() + 1, 20000000);
            PrimeFinderThread pft3b = new PrimeFinderThread(pft3.getLastNumberChecked() + 1, 30000000);

            pft1b.start();
            pft2b.start();
            pft3b.start();


        } catch (InterruptedException e) {
            System.err.println("El hilo principal fue interrumpido: " + e.getMessage());
        }
    }
}
