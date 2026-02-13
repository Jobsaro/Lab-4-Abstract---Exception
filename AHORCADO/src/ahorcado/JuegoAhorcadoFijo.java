/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ahorcado;

/**
 *
 * @author Administrator
 */
public class JuegoAhorcadoFijo extends JuegoAhorcadoBase {

    public JuegoAhorcadoFijo(String palabraFija) {
        super();
        this.palabraSecreta = palabraFija.toUpperCase();
        inicializarPalabraSecreta();
    }

    @Override
    public void inicializarPalabraSecreta() {
        generarGuiones();
    }

    @Override
    public void jugar() {
        // Reinicia para una nueva partida
        this.intentos = limiteIntentos;
        this.letrasUsadas.clear();
        this.figuraAhorcado.clear();
        generarGuiones(); //genera guiones otravesz
    }

    @Override
    public boolean verificarLetra(char letra) {
        letra = Character.toUpperCase(letra);
        letrasUsadas.add(letra);

        if (palabraSecreta.indexOf(letra) >= 0) {
            actualizarPalabraActual(letra);
            return true;
        } else {
            intentos--;
            //actualiza el muñequito 
            actualizarFigura(); 
            return false;
        }
    }

    @Override
    public void actualizarPalabraActual(char letra) {
        StringBuilder sb = new StringBuilder(palabraActual);
        for (int i = 0; i < palabraSecreta.length(); i++) {
            if (palabraSecreta.charAt(i) == letra) {
                sb.setCharAt(i, letra);
            }
        }
        palabraActual = sb.toString();
    }

    private void actualizarFigura() {
        // se agregan las partes del cuerpo segun los errores
        String[] partes = {"CABEZA", "CUERPO", "BRAZO_IZQ", "BRAZO_DER", "PIERNA_IZQ", "PIERNA_DER"};
        int indice = 6 - intentos - 1; 
        if (indice >= 0 && indice < partes.length) {
            figuraAhorcado.add(partes[indice]);
        }
    }
}