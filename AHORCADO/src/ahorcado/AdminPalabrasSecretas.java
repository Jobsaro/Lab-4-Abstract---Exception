package ahorcado;

import java.util.ArrayList;
import java.util.Random;

public class AdminPalabrasSecretas {
    // Agregamos 'static' para que todas las instancias compartan la misma lista
    private static ArrayList<String> palabras = new ArrayList<>();

    public AdminPalabrasSecretas() {
        // Solo inicializamos si la lista está vacía para no duplicar
        if (palabras.isEmpty()) {
            agregarPalabra("ABSTRACTA");
            agregarPalabra("ARMADILLO");
        }
    }

    public void agregarPalabra(String palabra) {
        if (!palabras.contains(palabra.toUpperCase())) {
            palabras.add(palabra.toUpperCase());
        }
    }

    public String obtenerPalabraAzar() {
        if (palabras.isEmpty()) return "DEFAULT";
        Random rand = new Random();
        return palabras.get(rand.nextInt(palabras.size()));
    }
}