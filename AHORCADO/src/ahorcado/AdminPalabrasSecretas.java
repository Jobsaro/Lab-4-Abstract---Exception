/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ahorcado;

import java.util.ArrayList;
import java.util.Random;

public class AdminPalabrasSecretas {
    private ArrayList<String> palabras;

    public AdminPalabrasSecretas() {
        palabras = new ArrayList<>();
        // se agregan las palabras
        agregarPalabra("ABSTRACTA");
        agregarPalabra("BISCOCHO");
        agregarPalabra("HERENCIA");
        agregarPalabra("SISTEMAS");
        agregarPalabra("ARMADILLO");
        agregarPalabra("AVESTRUZ");
    }

    // agrega nueva palabra
    public void agregarPalabra(String palabra) {
        if (!palabras.contains(palabra.toUpperCase())) {
            palabras.add(palabra.toUpperCase());
        }
    }

    // obtiene una palabra alazar
    public String obtenerPalabraAzar() {
        if (palabras.isEmpty()) return "DEFAULT";
        Random rand = new Random();
        return palabras.get(rand.nextInt(palabras.size()));
    }
}