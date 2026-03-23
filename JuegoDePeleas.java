import javax.swing.*;
import java.awt.*;
/**
 * La clase JuegoDePeleas representa un simulador de un juego de peleas con interfaz grafica sencilla.
 * Perimte mostrar una panatalla con los datos de quien realizo el codigo, los personajes y elmenu y opcioes del personaje.
 * @version 05/12/2024
 * @author Campos Vera José David
 */ 
public class JuegoDePeleas {
    private static JFrame frame;

    /**
     * Método principal de juego.
     * Inicia el programa mostranod la pantalla de datos.
     * 
     * @param args argumentos de la linea de comandos
     */
    public static void main(String[] args) {
        mostrarCreditos();
    }

    /**
     * Muestra la pantalla de de datos y el boton demo para continuar con la sellecion de personajes.
     */
    private static void mostrarCreditos() {
        frame = new JFrame("Proyecto");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        JLabel label = new JLabel("<html><center>Equipo: Equipo 0<br>Integrantes: Campos Vera José David<br>Grupo: 6° Semestre 3</center></html>", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 24));
        JButton btnDemo = new JButton("Demo");

        btnDemo.addActionListener(e -> mostrarSeleccionPersonajes());
        btnDemo.setFont(new Font("Arial", Font.BOLD, 18));

        frame.add(label, BorderLayout.CENTER);
        frame.add(btnDemo, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    /**
     * Muestra la pantalla de seleccion de peronsjes con una lista de personajes disponibles.
     * Permite elegir un personaje y mostar sus detalles.
     */
    // Pantalla de Selección de Personajes
    private static void mostrarSeleccionPersonajes() {
        frame.getContentPane().removeAll();
        frame.repaint();
        JLabel titulo = new JLabel("Personajes", SwingConstants.CENTER); 
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 3));
        // Crear personajes
        Personaje[] personajes = {
            new Personaje("Dragon negro de ojos rojos", "red eyes.png", new String[]{"Llamarada infernal", "Descarga de fuergo infernal", "Espiritu ojos rojos", "Colmillo ojos rojos", "Furia del ojos rojos"}),
            new Personaje("Dragon blanco de ojos azules", "blue eyes.png", new String[]{"Relampago blanco", "Ira de ojos azules", "Rayo del caos", "Colmillo ojos azules", "Alam de ojos azules"}),
            new Personaje("Mago oscuro", "dark magic.png", new String[]{"Magia de ilusión", "Secretos de la magia oscura", "Mil cucchillos", "Ataque magico oscuro", "Formula mágica"}),
            new Personaje("Slifer el dragon del cielo", "slifer.png", new String[]{"Rompe cielos", "Tornado celestial", "Ataque fuerzatrueno", "Furia de slifer", "Velocidad divina"}),
            new Personaje("El dragon alado de ra", "ra.png", new String[]{"Llamas doradas", "Pacto de Ra", "Vuelo celestial", "Explosión solar", "Furia divina"}),
            new Personaje("Obelisco el atormentador", "obelisco.png", new String[]{"Golpe del titán", "Puño destructor", "Impacto colosal", "Tributo del destructor", "Destruccion divina"})
        };

        for (Personaje p : personajes) {
            JButton btnPersonaje = new JButton(new ImageIcon(p.getImagen()));
            btnPersonaje.addActionListener(e -> mostrarVentanaPersonaje(p));
            panel.add(btnPersonaje);
        }
        frame.add(titulo, BorderLayout.NORTH);
        frame.add(panel);
        frame.revalidate();
    }

    /**
     * Muestra los detalles del personaje seleccionado en una nueva ventana
     * Muestra la imagen del personaje, sus barras de vida y energia, y sus ataques
     * 
     * @param personaje el personaje seleccionado
     */
    // Pantalla del Personaje Seleccionado
    private static void mostrarVentanaPersonaje(Personaje personaje) {
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.setLayout(new BorderLayout());

        // Título con el nombre del personaje
        JLabel labelTitulo = new JLabel(personaje.getNombre(), SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(labelTitulo, BorderLayout.NORTH);

        // Panel central con la imagen grande y las barras separadas
        JPanel panelCentro = new JPanel(new BorderLayout(20, 20));
        panelCentro.setBackground(Color.LIGHT_GRAY);

        JLabel labelImagen = new JLabel(new ImageIcon(new ImageIcon(personaje.getImagen()).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH)));
        labelImagen.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panelBarras = new JPanel();
        panelBarras.setLayout(new BoxLayout(panelBarras, BoxLayout.Y_AXIS));
        panelBarras.setBackground(Color.LIGHT_GRAY);

        BarraRectangulo barraEnergia = new BarraRectangulo(50000, new Color(255, 255, 0), "Energia", 400, 100);
        barraEnergia.actualizarValor(personaje.getKi());

        BarraRectangulo barraFuerza = new BarraRectangulo(50000, new Color(0, 255, 0), "Vida", 400, 100);
        barraFuerza.actualizarValor(personaje.getFuerza());

        panelBarras.add(Box.createVerticalStrut(150));
        panelBarras.add(barraEnergia);
        panelBarras.add(Box.createVerticalStrut(5)); // Espacio entre barras
        panelBarras.add(barraFuerza);
        panelCentro.add(labelImagen, BorderLayout.CENTER);
        panelCentro.add(panelBarras, BorderLayout.EAST);
        // Panel inferior con botones
        JPanel panelBotones = new JPanel(new GridLayout(2, 3, 10, 10));
        panelBarras.setBackground(Color.LIGHT_GRAY);
        // Botones de ataques
        for (String ataque : personaje.getAtaques()) {
            JButton btnAtaque = new JButton(ataque);
            btnAtaque.setPreferredSize(new Dimension(100, 50));
            btnAtaque.addActionListener(e -> {
                personaje.atacar();
                barraEnergia.actualizarValor(personaje.getKi());
                barraFuerza.actualizarValor(personaje.getFuerza());
                verificarCondiciones(personaje, frame);
                if (personaje.getKi() <= 0 || personaje.getFuerza() <= 0) {
                    System.exit(0);
                }
            });
            panelBotones.add(btnAtaque);
        }
        // Botón entrenar (universal)
        JButton btnEntrenar = new JButton("Entrenar");
        btnEntrenar.setPreferredSize(new Dimension(100, 50));
        btnEntrenar.setBackground(Color.BLUE);
        btnEntrenar.setForeground(Color.WHITE);
        btnEntrenar.addActionListener(e -> {
            personaje.entrenar();
            barraEnergia.actualizarValor(personaje.getKi());
            barraFuerza.actualizarValor(personaje.getFuerza());
        });
        panelBotones.add(btnEntrenar);
        // Botón salir
        JButton btnSalir = new JButton("Salir");
        btnSalir.setPreferredSize(new Dimension(100, 50));
        btnSalir.setBackground(Color.RED);
        btnSalir.setForeground(Color.WHITE);
        btnSalir.addActionListener(e -> System.exit(0));
        panelBotones.add(btnSalir);
        // Agregar componentes al frame
        frame.add(panelCentro, BorderLayout.CENTER);
        frame.add(panelBotones, BorderLayout.SOUTH);
        frame.revalidate();
    }

    /**
     * Veririfca si el personaje tiene ki o fuerza igual a 0 y cierra el programa en caso de ser asi.
     * 
     * @param personaje el personaje cuya condicion se va a verificar 
     * @param frame el frame de la vventana actual.
     */
    // Verificar si el ki o la fuerza es 0 y cerrar el programa
    private static void verificarCondiciones(Personaje personaje, JFrame frame) {
        if (personaje.getKi() <= 0 || personaje.getFuerza() <= 0) {
            JOptionPane.showMessageDialog(frame, "El personaje se ha quedado sin ki o fuerza. El personaje no puede atacar.");
            System.exit(0);
        }
    }
}
/**
 * Clase para representar una barra personalizada con una etiqueta y un color
 */
// Clase para las barras personalizadas
class BarraRectangulo extends JPanel {
    private int valorMaximo;
    private int valorActual;
    private Color color;
    private String etiqueta;
    private int width;
    private int height;

    /**
     * Constructor para la barra personalizada.
     * 
     * @param valorMaximo el valor máximo de la barra
     * @param color el color de la barra.
     * @param etiqueta la etiqueta de la barra 
     * @param width el ancho de la barra
     * @param height la altura de la barra.
     */

    public BarraRectangulo(int valorMaximo, Color color, String etiqueta, int width, int height) {
        this.valorMaximo = valorMaximo;
        this.valorActual = valorMaximo;
        this.color = color;
        this.etiqueta = etiqueta;
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.LIGHT_GRAY);
    }

    /**
     * Actualiza el valor de la barra y repinta el componente
     * 
     * @param nuevoValor el nuevo valor de la barra
     */

    public void actualizarValor(int nuevoValor) {
        this.valorActual = nuevoValor;
        repaint();
    }

    /**
     * Pinta el componente de la barra
     * 
     * @param g el objeto Graphics utlizado para dibujar el componente
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);
        int anchoRelleno = (int) ((double) valorActual / valorMaximo * width);
        g.setColor(color);
        g.fillRect(1, 1, anchoRelleno - 2, height - 2);
        g.setColor(Color.BLACK);
        g.drawString(etiqueta, 10, 20);
    }
}
/**
 * Clase para representar un personaje en el juego
 */

// Clase Personaje
class Personaje {
    private String nombre;
    private int ki;
    private int fuerza;
    private String imagen;
    private String[] ataques;

    /**
     * Constructor para crear un personaje
     * 
     * @param nombre el nombre del personaje
     * @param imagen la ruta de la imagen del personaje
     * @param ataques una lista de ataques del personaje
     */

    public Personaje(String nombre, String imagen, String[] ataques) {
        this.nombre = nombre;
        this.ki = 50000; // Valor inicial
        this.fuerza = 50000; // Valor inicial
        this.imagen = imagen; // Ruta de la imagen
        this.ataques = ataques;
    }

    /**
     * Obtiene el nombre del personaje.
     * 
     * @return el nombre del personaje
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Obtiene el valor del ki del personaje.
     * 
     * @return el valor del ki.
     */
    public int getKi() {
        return ki;
    }
    /** 
     * Obtiene el valor actual de la fuerza del personaje
     * 
     * @return el valor de la fuerza
     */

    public int getFuerza() {
        return fuerza;
    }

    /**
     * Obtiene la ruta de la imgen del personaje
     * 
     * @return la ruta de la imagen
     */

    public String getImagen() {
        return imagen;
    }

    /**
     * Obtiene la lista de ataques del personaje
     * 
     * @return una lista de ataques
     */

    public String[] getAtaques() {
        return ataques;
    }

    /**
     * Entrena al perosnaje aumentando sus valores de ki y fuerza.
     */

    public void entrenar() {
        ki *= 1.2;
        fuerza *= 1.2;
    }

    /**
     * Realiza un ataque disminuyendo los valores de ki y fuerza
     */
    public void atacar() {
        if (ki >= 800 && fuerza >= 800) {
            ki -= 10000;
            fuerza -= 10000;
        }
    }

    /**
     * Verifica si el personaje tiene ki o fuerza igual a 0 y cierra el programa en caso de ser asi.
     * 
     * @param personaje personaje cuya condicion se va a verificar
     * @param frame el frame de la ventana actual.
     */
    
    private static void verificarCondiciones(Personaje personaje, JFrame frame) { 
        if (personaje.getKi() <= 0 || personaje.getFuerza() <= 0) { 
            JOptionPane.showMessageDialog(frame, "El personaje se ha quedado sin energia. no es posible atacar."); 
            System.exit(0); 
        } 
    }
}
