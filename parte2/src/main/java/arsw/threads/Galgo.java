package arsw.threads;

/**
 * Un galgo que puede correr en un carril
 * 
 * @author rlopez
 * 
 */
public class Galgo extends Thread {
	private int paso;
	private Carril carril;
	RegistroLlegada regl;

	public Galgo(Carril carril, String name, RegistroLlegada reg) {
		super(name);
		this.carril = carril;
		paso = 0;
		this.regl = reg;
	}

	public void corra() throws InterruptedException {
		while (paso < carril.size()) {
    Thread.sleep(100);

    // Pausa: si pausado, esperar
    synchronized(MainCanodromo.lock) {
        while (MainCanodromo.pausado) {
            MainCanodromo.lock.wait();
        }
    }

    // Avanza el galgo
    carril.setPasoOn(paso++);
    carril.displayPasos(paso);

    // Llegada
    if (paso == carril.size()) {
        carril.finish();
        int ubicacion = regl.registrarLlegada(this.getName()); // usando método sincronizado
        System.out.println("El galgo " + this.getName() + " llegó en la posición " + ubicacion);
    }
}

	}

	@Override
	public void run() {

		try {
			corra();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
