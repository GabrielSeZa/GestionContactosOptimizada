package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import controlador.logica_ventana;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.BorderFactory;
import java.awt.Insets;

import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ventana extends JFrame {

	public JPanel contentPane; // Panel principal que contendrá todos los componentes de la interfaz.
	public JTextField txt_nombres; // Campo de texto para ingresar nombres.
	public JTextField txt_telefono; // Campo de texto para ingresar números de teléfono.
	public JTextField txt_email; // Campo de texto para ingresar direcciones de correo electrónico.
	public JTextField txt_buscar; // Campo de texto adicional.
	public JCheckBox chb_favorito; // Casilla de verificación para marcar un contacto como favorito.
	public JComboBox cmb_categoria; // Menú desplegable para seleccionar la categoría de contacto.
	public JButton btn_add; // Botón para agregar un nuevo contacto.
	public JButton btn_modificar; // Botón para modificar un contacto existente.
	public JButton btn_eliminar; // Botón para eliminar un contacto.
	public JComboBox cmb_idioma;
	public JList lst_contactos; // Lista para mostrar los contactos.
	public JScrollPane scrLista; // Panel de desplazamiento para la lista de contactos.
	public JTable tablaContactos;
	public DefaultTableModel modeloTabla;
	public JTabbedPane tabbedPane;
	public JPanel panelContactos;
	public JPanel panelEstadisticas;
	public JTextArea txt_estadisticas;
	public JPopupMenu menuContextual;
	public JMenuItem itemEliminar;
	
	public JLabel lbl_nombre;
	public JLabel lbl_telefono;
	public JLabel lbl_email;
	public JLabel lbl_buscar;
	public JLabel lbl_favorito;
	public JLabel lblTituloEst;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		 // Invoca el método invokeLater de la clase EventQueue para ejecutar la creación de la interfaz de usuario en un hilo de despacho de eventos (Event Dispatch Thread).
	    EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try {
	                // Dentro de este método, se crea una instancia de la clase ventana, que es la ventana principal de la aplicación.
	                ventana frame = new ventana();
	                // Establece la visibilidad de la ventana como verdadera, lo que hace que la ventana sea visible para el usuario.
	                frame.setVisible(true);
	            } catch (Exception e) {
	                // En caso de que ocurra una excepción durante la creación o visualización de la ventana, se imprime la traza de la pila de la excepción.
	                e.printStackTrace();
	            }
	        }
	    });
	}

	/**
	 * Create the frame.
	 */
	public ventana() {
		setTitle("GESTION DE CONTACTOS"); // Establece el título de la ventana.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define el comportamiento al cerrar la ventana.
		setResizable(false); // Evita que la ventana sea redimensionable.
		setBounds(100, 100, 1026, 748); // Establece el tamaño y la posición inicial de la ventana.
		contentPane = new JPanel(); // Crea un nuevo panel de contenido.
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // Establece un borde vacío alrededor del panel.

		setContentPane(contentPane); // Establece el panel de contenido como el panel principal de la ventana.
		contentPane.setLayout(null); // Configura el diseño del panel como nulo para posicionar manualmente los componentes.
		
		getContentPane().setBackground(new Color(230, 240, 250));
		
		tabbedPane = new JTabbedPane();
		tabbedPane.setBounds(10, 10, 990, 690);
		contentPane.add(tabbedPane);

		panelContactos = new JPanel();
		panelContactos.setLayout(null);
		tabbedPane.addTab("Contactos", panelContactos);
		panelContactos.setBackground(new Color(245, 248, 252));

		
		panelEstadisticas = new JPanel();
		panelEstadisticas.setLayout(null);
		tabbedPane.addTab("Estadísticas", panelEstadisticas);
		panelEstadisticas.setBackground(new Color(245, 248, 252));
		
		// Creación y configuración de etiquetas para los campos de entrada.
		lbl_nombre = new JLabel("NOMBRES:");
		lbl_nombre.setBounds(25, 41, 89, 13);
		lbl_nombre.setFont(new Font("Tahoma", Font.BOLD, 15));
		panelContactos.add(lbl_nombre);
		
		
		lbl_telefono = new JLabel("TELEFONO:");
		lbl_telefono.setBounds(25, 80, 89, 13);
		lbl_telefono.setFont(new Font("Tahoma", Font.BOLD, 15));
		panelContactos.add(lbl_telefono);
		
		lbl_email = new JLabel("EMAIL:");
		lbl_email.setBounds(25, 122, 89, 13);
		lbl_email.setFont(new Font("Tahoma", Font.BOLD, 15));
		panelContactos.add(lbl_email);
		
		lbl_buscar = new JLabel("BUSCAR POR NOMBRE:");
		lbl_buscar.setBounds(25, 661, 192, 13);
		lbl_buscar.setFont(new Font("Tahoma", Font.BOLD, 15));
		panelContactos.add(lbl_buscar);
		
		// Creación y configuración de campos de texto para ingresar nombres, teléfonos y correos electrónicos.
		txt_nombres = new JTextField(); // Campo de texto para nombres.
		txt_nombres.setBackground(Color.WHITE);
		txt_nombres.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		txt_nombres.setBounds(124, 28, 427, 31); // Define la posición y tamaño del campo de texto.
		txt_nombres.setFont(new Font("Tahoma", Font.PLAIN, 15)); // Configura la fuente del campo de texto.
		panelContactos.add(txt_nombres); // Agrega el campo de texto al panel de contenido.
		txt_nombres.setColumns(10); // Establece el número de columnas para el campo de texto.
		
		txt_telefono = new JTextField();
		txt_telefono.setBackground(Color.WHITE);
		txt_telefono.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		txt_telefono.setBounds(124, 69, 427, 31);
		txt_telefono.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txt_telefono.setColumns(10);
		panelContactos.add(txt_telefono);
		
		txt_email = new JTextField();
		txt_email.setBackground(Color.WHITE);
		txt_email.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		txt_email.setBounds(124, 110, 427, 31);
		txt_email.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txt_email.setColumns(10);
		panelContactos.add(txt_email);
		
		txt_buscar = new JTextField();
		txt_buscar.setBackground(Color.WHITE);
		txt_buscar.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		txt_buscar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txt_buscar.setColumns(10);
		txt_buscar.setBounds(212, 620, 784, 31);
		panelContactos.add(txt_buscar);
		
		// Creación y configuración de una casilla de verificación para indicar si un contacto es favorito.
		chb_favorito = new JCheckBox("CONTACTO FAVORITO"); // Casilla de verificación.
		chb_favorito.setBackground(new Color(245, 248, 252));
		chb_favorito.setBounds(24, 170, 193, 21); // Define la posición y tamaño de la casilla de verificación.
		chb_favorito.setFont(new Font("Tahoma", Font.PLAIN, 15)); // Configura la fuente de la casilla de verificación.
		panelContactos.add(chb_favorito); // Agrega la casilla de verificación al panel de contenido.

		
		cmb_categoria = new JComboBox(); // Crea un nuevo JComboBox para permitir la selección de categorías.
		cmb_categoria.setBounds(300, 167, 251, 31); // Establece la posición y el tamaño del JComboBox en el panel.
		panelContactos.add(cmb_categoria); // Agrega el JComboBox al panel de contenido.

		// Arreglo que contiene las categorías disponibles.
		String[] categorias = {"Elija una Categoria", "Familia", "Amigos", "Trabajo"};
		for (String categoria : categorias) {
		    // Agrega cada categoría al JComboBox.
		    cmb_categoria.addItem(categoria);
		}

		btn_add = new JButton("AGREGAR"); // Crea un nuevo botón con el texto "AGREGAR".
		btn_add.setBackground(new Color(0, 120, 215));
		btn_add.setForeground(Color.WHITE);
		btn_add.setFont(new Font("Tahoma", Font.PLAIN, 15)); // Configura la fuente del botón.
		btn_add.setBounds(601, 70, 125, 65); // Establece la posición y el tamaño del botón en el panel.
		panelContactos.add(btn_add); // Agrega el botón al panel de contenido.
		btn_add.setFocusPainted(false);

		btn_add.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		        btn_add.setBackground(new Color(0, 100, 190));
		    }
		    public void mouseExited(java.awt.event.MouseEvent evt) {
		        btn_add.setBackground(new Color(0, 120, 215));
		    }
		});
		
		btn_modificar = new JButton("MODIFICAR");
		btn_modificar.setBackground(new Color(255, 165, 0));
		btn_modificar.setForeground(Color.WHITE);
		btn_modificar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_modificar.setBounds(736, 70, 125, 65);
		panelContactos.add(btn_modificar);
		btn_modificar.setFocusPainted(false);

		btn_modificar.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		        btn_modificar.setBackground(new Color(230, 140, 0));
		    }
		    public void mouseExited(java.awt.event.MouseEvent evt) {
		        btn_modificar.setBackground(new Color(255, 165, 0));
		    }
		});
		
		btn_eliminar = new JButton("ELIMINAR");
		btn_eliminar.setBackground(new Color(220, 20, 60));
		btn_eliminar.setForeground(Color.WHITE);
		btn_eliminar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_eliminar.setBounds(871, 69, 125, 65);
		panelContactos.add(btn_eliminar);
		btn_eliminar.setFocusPainted(false);

		btn_eliminar.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		        btn_eliminar.setBackground(new Color(180, 0, 40));
		    }
		    public void mouseExited(java.awt.event.MouseEvent evt) {
		        btn_eliminar.setBackground(new Color(220, 20, 60));
		    }
		});
		
		cmb_idioma = new JComboBox();
		cmb_idioma.setBounds(800, 20, 150, 30);
		cmb_idioma.addItem("Español");
		cmb_idioma.addItem("English");
		cmb_idioma.addItem("Français");
		panelContactos.add(cmb_idioma);
		
		String[] columnas = {"NOMBRE", "TELEFONO", "EMAIL", "CATEGORIA"};

		modeloTabla = new DefaultTableModel(columnas, 0);
		tablaContactos = new JTable(modeloTabla);
		
		tablaContactos.setBackground(Color.WHITE);
		tablaContactos.setForeground(Color.BLACK);
		tablaContactos.setRowHeight(25);
		tablaContactos.setSelectionBackground(new Color(0, 120, 215));
		tablaContactos.setSelectionForeground(Color.WHITE);
		
		tablaContactos.getTableHeader().setBackground(new Color(0, 120, 215));
		tablaContactos.getTableHeader().setForeground(Color.WHITE);
		tablaContactos.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
		
		tablaContactos.setShowGrid(true);
		tablaContactos.setGridColor(new Color(220, 220, 220));
		
		tablaContactos.setAutoCreateRowSorter(true);

		scrLista = new JScrollPane(tablaContactos);
		scrLista.setBounds(25, 242, 971, 360);
		panelContactos.add(scrLista);
		
		menuContextual = new JPopupMenu();
		itemEliminar = new JMenuItem("Eliminar");
		menuContextual.add(itemEliminar);

		tablaContactos.setComponentPopupMenu(menuContextual);
		
		lblTituloEst = new JLabel("ESTADISTICAS DE CONTACTOS");
		lblTituloEst.setBounds(30, 30, 400, 30);
		lblTituloEst.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelEstadisticas.add(lblTituloEst);

		txt_estadisticas = new JTextArea();
		txt_estadisticas.setBackground(Color.WHITE);
		txt_estadisticas.setMargin(new Insets(10,10,10,10));
		txt_estadisticas.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
		txt_estadisticas.setEditable(false);
		txt_estadisticas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txt_estadisticas.setBounds(30, 80, 400, 200);
		txt_estadisticas.setText(
		    "Total de contactos: 0\n" +
		    "Contactos favoritos: 0\n" +
		    "Categorías registradas: 0"
		);
		panelEstadisticas.add(txt_estadisticas);
	}
}
