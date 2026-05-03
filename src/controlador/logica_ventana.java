package controlador;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

import java.util.Locale;
import java.util.ResourceBundle;

import java.awt.event.ItemEvent;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.HashSet;
import java.util.Set;

import vista.ventana;
import modelo.*;

//Definición de la clase logica_ventana que implementa tres interfaces para manejar eventos.
public class logica_ventana implements ActionListener, ItemListener {
	private ventana delegado; // Referencia a la ventana principal que contiene la GUI.
	private String nombres, email, telefono, categoria=""; // Variables para almacenar datos del contacto.
	private persona persona; // Objeto de tipo persona, que representa un contacto.
	private List<persona> contactos; // Lista de objetos persona que representa todos los contactos.
	private boolean favorito = false; // Booleano que indica si un contacto es favorito.
	private TableRowSorter<javax.swing.table.DefaultTableModel> sorter;
	private ResourceBundle mensajes;

	// Constructor que inicializa la clase y configura los escuchadores de eventos para los componentes de la GUI.
	public logica_ventana(ventana delegado) {
		  // Asigna la ventana recibida como parámetro a la variable de instancia delegado.
	    this.delegado = delegado;
	    
	    Locale locale = new Locale("es"); // idioma inicial
	    mensajes = ResourceBundle.getBundle("mensajes", locale);
	    
	    sorter = new TableRowSorter<>(this.delegado.modeloTabla);
	    this.delegado.tablaContactos.setRowSorter(sorter);
	    // Carga los contactos almacenados al inicializar.
	    cargarContactosRegistrados(); 
	    // Registra los ActionListener para los botones de la GUI.
	    this.delegado.btn_add.addActionListener(this);
	    this.delegado.btn_eliminar.addActionListener(this);
	    this.delegado.btn_modificar.addActionListener(this);
	    this.delegado.itemEliminar.addActionListener(this);
	    
	    this.delegado.cmb_idioma.addItemListener(e -> {
	        if (e.getStateChange() == ItemEvent.SELECTED) {
	            cambiarIdioma();
	        }
	    });
	    
	    actualizarTextos();
	    
	    this.delegado.txt_buscar.getDocument().addDocumentListener(new DocumentListener() {


	    
	        @Override
	        public void insertUpdate(DocumentEvent e) {
	            filtrarTabla();
	        }

	        @Override
	        public void removeUpdate(DocumentEvent e) {
	            filtrarTabla();
	        }

	        @Override
	        public void changedUpdate(DocumentEvent e) {
	            filtrarTabla();
	        }
	    });
	    
	    this.delegado.tablaContactos.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            if (e.getKeyCode() == KeyEvent.VK_DELETE) {
	                eliminarSeleccionado();
	            }
	        }
	    });
	    
	    this.delegado.tablaContactos.addMouseListener(new java.awt.event.MouseAdapter() {
	        @Override
	        public void mouseClicked(java.awt.event.MouseEvent e) {
	            int filaSeleccionada = delegado.tablaContactos.getSelectedRow();

	            if (filaSeleccionada != -1) {
	                int filaModelo = delegado.tablaContactos.convertRowIndexToModel(filaSeleccionada);

	                delegado.txt_nombres.setText(contactos.get(filaModelo).getNombre());
	                delegado.txt_telefono.setText(contactos.get(filaModelo).getTelefono());
	                delegado.txt_email.setText(contactos.get(filaModelo).getEmail());
	                delegado.cmb_categoria.setSelectedItem(contactos.get(filaModelo).getCategoria());
	                delegado.chb_favorito.setSelected(contactos.get(filaModelo).isFavorito());
	            }
	        }
	    });
	    // Registra los ItemListener para el JComboBox de categoría y el JCheckBox de favoritos.
	    this.delegado.cmb_categoria.addItemListener(this);
	    this.delegado.chb_favorito.addItemListener(this);
	}
	
	private void cambiarIdioma() {
	    int opcion = delegado.cmb_idioma.getSelectedIndex();

	    if (opcion == 0) {
	        mensajes = ResourceBundle.getBundle("mensajes", new Locale("es"));
	    } else if (opcion == 1) {
	        mensajes = ResourceBundle.getBundle("mensajes", new Locale("en"));
	    } else if (opcion == 2) {
	        mensajes = ResourceBundle.getBundle("mensajes", new Locale("fr"));
	    }

	    actualizarTextos();
	
	}

	private void actualizarTextos() {
	    // Botones
	    delegado.btn_add.setText(mensajes.getString("agregar"));
	    delegado.btn_modificar.setText(mensajes.getString("modificar"));
	    delegado.btn_eliminar.setText(mensajes.getString("eliminar"));

	    // Labels
	    delegado.lbl_nombre.setText(mensajes.getString("nombre"));
	    delegado.lbl_telefono.setText(mensajes.getString("telefono"));
	    delegado.lbl_email.setText(mensajes.getString("email"));
	    delegado.lbl_buscar.setText(mensajes.getString("buscar"));

	    // Checkbox
	    delegado.chb_favorito.setText(mensajes.getString("favorito"));

	    // Título ventana
	    delegado.setTitle(mensajes.getString("titulo"));

	    // Tabs
	    delegado.tabbedPane.setTitleAt(0, mensajes.getString("contactos"));
	    delegado.tabbedPane.setTitleAt(1, mensajes.getString("estadisticas"));

	    // Estadísticas título
	    delegado.lblTituloEst.setText(mensajes.getString("estadisticas"));
	}
	
	private void filtrarTabla() {
	    String texto = delegado.txt_buscar.getText().trim();

	    if (texto.isEmpty()) {
	        sorter.setRowFilter(null);
	    } else {
	        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, 0));
	    }
	}

	// Método privado para inicializar las variables con los valores ingresados en la GUI.
	private void inicializacionCampos() {
		// Obtiene el texto ingresado en los campos de nombres, email y teléfono de la GUI.
		nombres = delegado.txt_nombres.getText();
		email = delegado.txt_email.getText();
		telefono = delegado.txt_telefono.getText();
	}

	// Método privado para cargar los contactos almacenados desde un archivo.
	private void cargarContactosRegistrados() {
	    try {
	        contactos = new personaDAO(new persona()).leerArchivo();

	        // Limpiar la tabla antes de cargar datos
	        delegado.modeloTabla.setRowCount(0);

	        // Agregar cada contacto a la tabla
	        for (persona contacto : contactos) {
	            delegado.modeloTabla.addRow(new Object[]{
	                contacto.getNombre(),
	                contacto.getTelefono(),
	                contacto.getEmail(),
	                contacto.getCategoria()
	            });
	        }
	        
	        actualizarEstadisticas();

	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(delegado, "Existen problemas al cargar todos los contactos");
	    }
	}

	// Método privado para limpiar los campos de entrada en la GUI y reiniciar variables.
	private void limpiarCampos() {
		// Limpia los campos de nombres, email y teléfono en la GUI.
	    delegado.txt_nombres.setText("");
	    delegado.txt_telefono.setText("");
	    delegado.txt_email.setText("");
	    // Reinicia las variables de categoría y favorito.
	    categoria = "";
	    favorito = false;
	    // Desmarca la casilla de favorito y establece la categoría por defecto.
	    delegado.chb_favorito.setSelected(favorito);
	    delegado.cmb_categoria.setSelectedIndex(0);
	    // Reinicia las variables con los valores actuales de la GUI.
	    inicializacionCampos();
	    // Recarga los contactos en la lista de contactos de la GUI.
	    
	}
	
	private void eliminarSeleccionado() {
	    int filaSeleccionada = delegado.tablaContactos.getSelectedRow();

	    if (filaSeleccionada != -1) {
	        int filaModelo = delegado.tablaContactos.convertRowIndexToModel(filaSeleccionada);
	        delegado.modeloTabla.removeRow(filaModelo);
	        contactos.remove(filaModelo);
	        actualizarEstadisticas();

	        JOptionPane.showMessageDialog(delegado, "Contacto eliminado correctamente");
	    } else {
	        JOptionPane.showMessageDialog(delegado, "Seleccione un contacto para eliminar");
	    }
	}
	
	private void actualizarEstadisticas() {
	    int total = contactos.size();
	    int favoritosCount = 0;

	    Set<String> categorias = new HashSet<>();

	    for (persona p : contactos) {
	        if (p.isFavorito()) {
	            favoritosCount++;
	        }

	        if (p.getCategoria() != null && !p.getCategoria().trim().isEmpty()) {
	            categorias.add(p.getCategoria());
	        }
	    }

	    delegado.txt_estadisticas.setText(
	        "Total de contactos: " + total + "\n" +
	        "Contactos favoritos: " + favoritosCount + "\n" +
	        "Categorías registradas: " + categorias.size()
	    );
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		inicializacionCampos();

	    if (e.getSource() == delegado.btn_add) {

	        if ((!nombres.equals("")) && (!telefono.equals("")) && (!email.equals(""))) {

	            if ((!categoria.equals("Elija una Categoria")) && (!categoria.equals(""))) {

	                persona = new persona(nombres, telefono, email, categoria, favorito);
	                new personaDAO(persona).escribirArchivo();

	                contactos.add(persona);
	                delegado.modeloTabla.addRow(new Object[]{
	                    persona.getNombre(),
	                    persona.getTelefono(),
	                    persona.getEmail(),
	                    persona.getCategoria()
	                });

	                limpiarCampos();
	                
	                actualizarEstadisticas();
	                
	                JOptionPane.showMessageDialog(delegado, mensajes.getString("msg_registrado"));
	            } else {
	                JOptionPane.showMessageDialog(delegado, "Elija una Categoria!!!");
	            }

	        } else {
	            JOptionPane.showMessageDialog(delegado, "Todos los campos deben ser llenados!!!");
	        }
	        
	    } else if (e.getSource() == delegado.btn_eliminar || e.getSource() == delegado.itemEliminar) {
	        eliminarSeleccionado();
	        

	    } else if (e.getSource() == delegado.btn_modificar) {

	        int filaSeleccionada = delegado.tablaContactos.getSelectedRow();

	        if (filaSeleccionada != -1) {
	            int filaModelo = delegado.tablaContactos.convertRowIndexToModel(filaSeleccionada);

	            contactos.get(filaModelo).setNombre(delegado.txt_nombres.getText());
	            contactos.get(filaModelo).setTelefono(delegado.txt_telefono.getText());
	            contactos.get(filaModelo).setEmail(delegado.txt_email.getText());
	            contactos.get(filaModelo).setCategoria(delegado.cmb_categoria.getSelectedItem().toString());
	            contactos.get(filaModelo).setFavorito(delegado.chb_favorito.isSelected());

	            delegado.modeloTabla.setValueAt(contactos.get(filaModelo).getNombre(), filaModelo, 0);
	            delegado.modeloTabla.setValueAt(contactos.get(filaModelo).getTelefono(), filaModelo, 1);
	            delegado.modeloTabla.setValueAt(contactos.get(filaModelo).getEmail(), filaModelo, 2);
	            delegado.modeloTabla.setValueAt(contactos.get(filaModelo).getCategoria(), filaModelo, 3);

	            actualizarEstadisticas();
	            JOptionPane.showMessageDialog(delegado, "Contacto modificado correctamente");
	            limpiarCampos();
	        } else {
	            JOptionPane.showMessageDialog(delegado, "Seleccione un contacto para modificar");
	        }
	    }

	}
	
	// Método privado para cargar los datos del contacto seleccionado en los campos de la GUI.
	private void cargarContacto(int index) {
		// Establece el nombre del contacto en el campo de texto de nombres.
	    delegado.txt_nombres.setText(contactos.get(index).getNombre());
	    // Establece el teléfono del contacto en el campo de texto de teléfono.
	    delegado.txt_telefono.setText(contactos.get(index).getTelefono());
	    // Establece el correo electrónico del contacto en el campo de texto de correo electrónico.
	    delegado.txt_email.setText(contactos.get(index).getEmail());
	    // Establece el estado de favorito del contacto en el JCheckBox de favorito.
	    delegado.chb_favorito.setSelected(contactos.get(index).isFavorito());
	    // Establece la categoría del contacto en el JComboBox de categoría.
	    delegado.cmb_categoria.setSelectedItem(contactos.get(index).getCategoria());
	}

	// Método que maneja los eventos de cambio de estado en los componentes cmb_categoria y chb_favorito.
	@Override
	public void itemStateChanged(ItemEvent e) {
		// Verifica si el evento proviene del JComboBox de categoría.
	    if (e.getSource() == delegado.cmb_categoria) {
	        // Obtiene el elemento seleccionado en el JComboBox y lo convierte en una cadena.
	        categoria = delegado.cmb_categoria.getSelectedItem().toString();
	        // Actualiza la categoría seleccionada en la variable "categoria".
	    } else if (e.getSource() == delegado.chb_favorito) {
	        // Verifica si el evento proviene del JCheckBox de favorito.
	        favorito = delegado.chb_favorito.isSelected();
	        // Obtiene el estado seleccionado del JCheckBox y actualiza el estado de favorito en la variable "favorito".
	    }
	}
}