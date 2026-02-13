/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ahorcado;

/**
 *
 * @author Administrator
 */
public class JuegoAhorcadoAzar extends JuegoAhorcadoBase {
    
    private AdminPalabrasSecretas admin;

    public JuegoAhorcadoAzar() {
        super();
        admin = new AdminPalabrasSecretas();
        inicializarPalabraSecreta(); // Selecciona la palabra
    }

    @Override
    public void inicializarPalabraSecreta() {
        this.palabraSecreta = admin.obtenerPalabraAzar();
        generarGuiones();
    }

    @Override
    public void jugar() {
        // Selecciona una palabra y se reinicia
        inicializarPalabraSecreta(); 
        this.intentos = limiteIntentos;
        this.letrasUsadas.clear();
        this.figuraAhorcado.clear();
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
        String[] partes = {"CABEZA", "CUERPO", "BRAZO_IZQ", "BRAZO_DER", "PIERNA_IZQ", "PIERNA_DER"};
        int indice = 6 - intentos - 1; 
        if (indice >= 0 && indice < partes.length) {
            figuraAhorcado.add(partes[indice]);
        }
    }
}