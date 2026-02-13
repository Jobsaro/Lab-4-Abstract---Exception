package ahorcado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

/**
 * Integrante: [Jocsan GOMEZ]
 */
public class AHORCADO extends JFrame {

    private JPanel panelDibujo;
    private JLabel lblPalabra;
    private JLabel lblIntentos;
    private JLabel lblMensaje;
    
    private JTextField txtEntrada;
    private JButton btnProbar;
    
    private JuegoAhorcadoBase juegoActual;

    public AHORCADO() {
        setTitle("Juego del Ahorcado - Laboratorio 4");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        mostrarMenu();
    }

    private void mostrarMenu() {
        getContentPane().removeAll();
        JPanel menu = new JPanel(new GridBagLayout());
        menu.setBackground(new Color(30, 30, 30));

        JLabel titulo = new JLabel("AHORCADO");
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setForeground(Color.CYAN);

        JButton btnFijo = new JButton("Jugar - Palabra Fija");
        JButton btnAzar = new JButton("Jugar - Palabra Azar");
        
        // --- AGREGADO: Botón Salir ---
        JButton btnSalir = new JButton("Salir");

        btnFijo.addActionListener(e -> iniciarJuego(new JuegoAhorcadoFijo("MELOCOTON")));
        btnAzar.addActionListener(e -> iniciarJuego(new JuegoAhorcadoAzar()));
        
        // --- AGREGADO: Acción para cerrar ---
        btnSalir.addActionListener(e -> System.exit(0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Para que los botones se vean del mismo tamaño
        gbc.fill = GridBagConstraints.HORIZONTAL; 

        gbc.gridx = 0; gbc.gridy = 0; menu.add(titulo, gbc);
        gbc.gridy = 1; menu.add(btnFijo, gbc);
        gbc.gridy = 2; menu.add(btnAzar, gbc);
        
        gbc.gridy = 3; menu.add(btnSalir, gbc);

        add(menu);
        revalidate();
        repaint();
    }

    private void iniciarJuego(JuegoAhorcadoBase juego) {
        this.juegoActual = juego;
        juego.jugar(); 
        construirInterfazJuego();
    }

    private void construirInterfazJuego() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        // panel de dibujkol
        panelDibujo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarAhorcado((Graphics2D) g);
            }
        };
        panelDibujo.setPreferredSize(new Dimension(300, 0));
        panelDibujo.setBackground(Color.WHITE);

        // panel central
        JPanel panelInfo = new JPanel(new GridLayout(4, 1));
        panelInfo.setBackground(new Color(45, 45, 45));

        lblPalabra = new JLabel(juegoActual.getPalabraActual(), SwingConstants.CENTER);
        lblPalabra.setFont(new Font("Monospaced", Font.BOLD, 40));
        lblPalabra.setForeground(Color.WHITE);

        // Contador
        lblIntentos = new JLabel("Intentos restantes: " + juegoActual.getIntentos(), SwingConstants.CENTER);
        lblIntentos.setFont(new Font("Arial", Font.BOLD, 20));
        lblIntentos.setForeground(Color.ORANGE);

        lblMensaje = new JLabel("Ingrese una letra", SwingConstants.CENTER);
        lblMensaje.setForeground(Color.LIGHT_GRAY);

        panelInfo.add(lblIntentos);
        panelInfo.add(lblPalabra);
        panelInfo.add(lblMensaje);

        //panel inferior
        JPanel panelEntrada = new JPanel();
        panelEntrada.setBackground(Color.DARK_GRAY);
        
        txtEntrada = new JTextField(5);
        txtEntrada.setFont(new Font("Arial", Font.BOLD, 20));
        
        btnProbar = new JButton("Probar Letra");
        btnProbar.addActionListener(e -> procesarEntrada());
        
        // usar enter
        txtEntrada.addActionListener(e -> procesarEntrada());

        panelEntrada.add(new JLabel("Letra: "));
        panelEntrada.add(txtEntrada);
        panelEntrada.add(btnProbar);

        add(panelDibujo, BorderLayout.WEST);
        add(panelInfo, BorderLayout.CENTER);
        add(panelEntrada, BorderLayout.SOUTH);
        
        // Poner el foco en la caja de texto
        txtEntrada.requestFocusInWindow();
        
        revalidate();
        repaint();
    }

    private void procesarEntrada() {
        String texto = txtEntrada.getText().trim();
        
        // Limpia el campo de texto 
        txtEntrada.setText("");
        txtEntrada.requestFocus();

        try {
            // validacion
            if (texto.isEmpty()) {
                throw new JuegoException("Debes escribir una letra.");
            }
            char letra = texto.charAt(0);

            // validar reglas
            juegoActual.validarEntrada(letra);

            // verifica a si acierta o no
            boolean acierto = juegoActual.verificarLetra(letra);

            if (acierto) {
                lblMensaje.setText("Correcto! Ingrese otra letra.");
                lblMensaje.setForeground(Color.GREEN);
            } else {
                lblMensaje.setText("Fallaste, Ingrese una letra.");
                lblMensaje.setForeground(Color.RED);
            }
            
            actualizarEstadoVisual();

        } catch (JuegoException ex) {
            // captura excepecion
            lblMensaje.setText(ex.getMessage());
            lblMensaje.setForeground(Color.YELLOW);
        } catch (Exception ex) {
            lblMensaje.setText("Error inesperado.");
        }
    }

    private void actualizarEstadoVisual() {
        lblPalabra.setText(juegoActual.getPalabraActual());
        lblIntentos.setText("Intentos restantes: " + juegoActual.getIntentos());
        panelDibujo.repaint();

        // verifica fin de juego
        if (juegoActual.hasGanado()) {
            finalizarJuego("VICTORIA!!!!!!!");
        } else if (juegoActual.getIntentos() <= 0) {
            finalizarJuego("DERROTA. La palabra era: " + juegoActual.getPalabraSecreta());
        }
    }

    private void finalizarJuego(String mensaje) {
        txtEntrada.setEnabled(false);
        btnProbar.setEnabled(false);
        lblMensaje.setText(mensaje);
        
        Calendar cal = Calendar.getInstance();
        System.out.println("Fin: " + cal.getTime());

        JOptionPane.showMessageDialog(this, mensaje);
        
        int opt = JOptionPane.showConfirmDialog(this, "Jugar otra vez?", "Fin", JOptionPane.YES_NO_OPTION);
        
        if (opt == JOptionPane.YES_OPTION) {
            mostrarMenu();
        } else if (opt == JOptionPane.NO_OPTION) {
            mostrarMenu();
        }
    }

    private void dibujarAhorcado(Graphics2D g) {
        g.setStroke(new BasicStroke(3));
        g.setColor(Color.BLACK);
        
        // Base
        g.drawLine(50, 400, 250, 400); 
        g.drawLine(150, 400, 150, 100); 
        g.drawLine(150, 100, 250, 100); 
        g.drawLine(250, 100, 250, 150); 

        // lista de figura
        java.util.List<String> partes = juegoActual.getFiguraAhorcado();
        
        if (partes.contains("CABEZA")) g.drawOval(230, 150, 40, 40);
        if (partes.contains("CUERPO")) g.drawLine(250, 190, 250, 300);
        if (partes.contains("BRAZO_IZQ")) g.drawLine(250, 210, 220, 260);
        if (partes.contains("BRAZO_DER")) g.drawLine(250, 210, 280, 260);
        if (partes.contains("PIERNA_IZQ")) g.drawLine(250, 300, 220, 360);
        if (partes.contains("PIERNA_DER")) g.drawLine(250, 300, 280, 360);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AHORCADO().setVisible(true));
    }
}