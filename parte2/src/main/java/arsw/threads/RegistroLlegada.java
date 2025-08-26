package arsw.threads;

public class RegistroLlegada {

	private int ultimaPosicionAlcanzada=1;

	private String ganador=null;
	
	 public synchronized int registrarLlegada(String nombreGalgo) {
        int posicion = ultimaPosicionAlcanzada++;
        if (posicion == 1) {
            ganador = nombreGalgo;
        }
        return posicion;
    }

    public synchronized String getGanador() {
        return ganador;
    }

	public synchronized int getUltimaPosicionAlcanzada() {
    return ultimaPosicionAlcanzada;
}

}
