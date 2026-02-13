/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ahorcado;

import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public abstract class JuegoAhorcadoBase implements JuegoAhorcado {

    protected String palabraSecreta;
    protected String palabraActual;
    protected int intentos;
    protected int limiteIntentos = 6;
    protected ArrayList<Character> letrasUsadas;
    protected ArrayList<String> figuraAhorcado; 

    public JuegoAhorcadoBase() {
        this.intentos = limiteIntentos;
        this.letrasUsadas = new ArrayList<>();
        this.figuraAhorcado = new ArrayList<>();
    }

    // metodos abstractos
    public abstract void actualizarPalabraActual(char letra);
    public abstract boolean verificarLetra(char letra);

    // metodo hasGanado
    public boolean hasGanado() {
        return !palabraActual.contains("_");
    }

    // metodo para los guiones
    protected void generarGuiones() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < palabraSecreta.length(); i++) {
            sb.append("_");
        }
        palabraActual = sb.toString();
    }
    
    // metodo para validar la 
    public void validarEntrada(char letra) throws JuegoException {
        if (!Character.isLetter(letra)) {
            throw new JuegoException("Solo se permiten letras.");
        }
        if (letrasUsadas.contains(Character.toUpperCase(letra))) {
            throw new JuegoException("Letra repetida.");
        }
    }

    // getters para la GUI
    public String getPalabraActual() { return palabraActual; }
    public int getIntentos() { return intentos; }
    public String getPalabraSecreta() { return palabraSecreta; }
    public ArrayList<String> getFiguraAhorcado() { return figuraAhorcado; }
}