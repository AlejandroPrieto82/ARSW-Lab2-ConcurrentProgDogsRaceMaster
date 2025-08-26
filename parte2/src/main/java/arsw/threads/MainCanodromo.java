package arsw.threads;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import arsw.threads.Galgo;
import arsw.threads.Canodromo;
import arsw.threads.RegistroLlegada;
import arsw.threads.Carril;



public class MainCanodromo {
    
    public static final Object lock = new Object();
    public static volatile boolean pausado = false;


    private static Galgo[] galgos;
    private static Canodromo can;
    private static RegistroLlegada reg = new RegistroLlegada();

    public static void main(String[] args) {
        can = new Canodromo(17, 100);
        galgos = new Galgo[can.getNumCarriles()];
        can.setVisible(true);

        // Acción del botón start
        can.setStartAction(
                new ActionListener() {

                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        // como acción, se crea un nuevo hilo que cree los hilos
                        // 'galgos', los pone a correr, y luego muestra los resultados.
                        // La acción del botón se realiza en un hilo aparte para evitar
                        // bloquear la interfaz gráfica.
                        ((JButton) e.getSource()).setEnabled(false);
                        new Thread() {
                            public void run() {
                                for (int i = 0; i < can.getNumCarriles(); i++) {
                                    // crea los hilos 'galgos'
                                    galgos[i] = new Galgo(can.getCarril(i), "" + i, reg);
                                    // inicia los hilos
                                    galgos[i].start();

                                }

                                for (int i = 0; i < galgos.length; i++) {
                                    try {
                                        galgos[i].join();
                                    } catch (InterruptedException ex) {
                                        ex.printStackTrace();
                                    }
                                }

                                can.winnerDialog(reg.getGanador(), reg.getUltimaPosicionAlcanzada() - 1);
                                System.out.println("El ganador fue:" + reg.getGanador());
                            }
                        }.start();

                    }
                });

        can.setStopAction(e -> {
    synchronized(lock) {
        pausado = true; 
    }
    System.out.println("Carrera pausada!");
});

        can.setContinueAction(e -> {
    synchronized(lock) {
        pausado = false; 
        lock.notifyAll(); 
    }
    System.out.println("Carrera reanudada!");
});

    }

}
