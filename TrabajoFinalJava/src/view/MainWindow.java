/*
 * Autor Carlos Michelena Rueda
 * Fecha: 09/11/2025
 */

package view;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Data;

public class MainWindow extends JFrame {

	private JButton save;
	private ArrayList<JTextField> textFields = new ArrayList<JTextField>();
	private ArrayList<JLabel> labels = new ArrayList<JLabel>();
	private ArrayList<JPanel> panels = new ArrayList<JPanel>();
	private JPanel panelGroup;
	private JComboBox<String> comboTablas;
	private DefaultTableModel tableDB;
	private JTable table;

	public MainWindow(Data data) {

		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setSize(500, 350);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		createPanelNorth();
		createSaveButton(data);

		setVisible(true);
	}

	// -----------------------
	// -- Getters y Setters --

	public JButton getSave() {
		return save;
	}

	public void setSave(JButton save) {
		this.save = save;
	}

	public ArrayList<JTextField> getTextFields() {
		return textFields;
	}

	public void setTextFields(ArrayList<JTextField> textFields) {
		this.textFields = textFields;
	}

	public ArrayList<JLabel> getLabels() {
		return labels;
	}

	public void setLabels(ArrayList<JLabel> labels) {
		this.labels = labels;
	}

	public ArrayList<JPanel> getPanels() {
		return panels;
	}

	public void setPanels(ArrayList<JPanel> panels) {
		this.panels = panels;
	}

	public JPanel getPanelGroup() {
		return panelGroup;
	}

	public void setPanelGroup(JPanel panelGroup) {
		this.panelGroup = panelGroup;
	}

	public JComboBox<String> getComboTablas() {
		return comboTablas;
	}

	public void setComboTablas(JComboBox<String> comboTablas) {
		this.comboTablas = comboTablas;
	}

	public DefaultTableModel getTableDB() {
		return tableDB;
	}

	public void setTableDB(DefaultTableModel tableDB) {
		this.tableDB = tableDB;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	// -- Fin Getters y Setters --
	// ---------------------------

	/* 
	 * Creación del botón de guardado para que la id interna del propio botón no cambie
	 * y se pueda reutilizar aunque se refresque la vista
	 */
	private void createSaveButton(Data data) {

		save = new JButton(data.getTexts().get(0));
	}

	// Creación de la parte norte de la vista
	public void createPanelNorth() {

		JPanel panelNorth = new JPanel(new FlowLayout());
		comboTablas = new JComboBox<String>();
		panelNorth.add(comboTablas);
		panels.add(panelNorth);
		add(panels.get(0), BorderLayout.NORTH);
	}

	// Creación del panel central que recibe el número de columnas y un ArrayList con los nombres de las columnas
	public void createPanelCenter(int numberColumns, ArrayList<String> columnsNames) {

		/* 
		 * Comprobamos si "panelGroup" está vacío o no.
		 * En el caso de que no lo esté limpiamos la vista y eliminamos la posición 1 del ArrayList 
		 * Para poder reutilizar dicha posición
		 */ 
		if (panelGroup != null) {

			this.remove(panels.get(1));
			panels.remove(1);
		}

		/* 
		 * Creación de "panelGroup", contiene tanto la tabla del panel central 
		 * como los textField y las labels del panel sur
		 */
		panelGroup = new JPanel(new BorderLayout());
		panels.add(panelGroup);
		
		JPanel panelCenter = new JPanel(new FlowLayout());
		
		// Definimos la tabla con el modelo "DefaultTableModel"
		tableDB = new DefaultTableModel();

		// Cogemos el numero de columnas que ha recibido el método para 
		for (int i = 0; i < numberColumns; i++) {

			/* 
			 * Aquí cogemos el ArrayList con los nombres de las columnas
			 * para añadirlas a la tabla
			 */
			tableDB.addColumn(columnsNames.get(i));
		}

		table = new JTable(tableDB);

		// Desactiva la de autoajuste de las dimensiones de la tabla
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		// Fijamos un ancho para las columnas
		int fixedWidth = 80;

		for (int i = 0; i < table.getColumnCount(); i++) {

			// Establecemos el ancho definido antes para cada columna
			table.getColumnModel().getColumn(i).setPreferredWidth(fixedWidth);
		}

		// Creamos una variable que nos va a calcular el ancho de la propia tabla
		int wantedWidth = table.getColumnCount() * fixedWidth;

		/*
		 *  Añadimos la dimension de ancho que definimos justo arriba
		 *  y le añadimos tambien la altura
		 */
		table.setPreferredScrollableViewportSize(new Dimension(wantedWidth, 400));

		// Creamos el ScrollPane y añadimos la tabla
		JScrollPane scrollPane = new JScrollPane(table);
		
		// Añadimos la tabla al panel central y este panel al panelGroup
		panelCenter.add(scrollPane);
		panelGroup.add(panelCenter, BorderLayout.NORTH);

		// Refrescamos la ventana
		this.revalidate();
		this.repaint();
	}

	public void createPanelSouth(ArrayList<String> columnsNames) {

		// Asegurar que las etiquetas y textFields estén vacíos
		labels.clear();
		textFields.clear();

		// Panel que posteriormente se añadira a panelGroup
		JPanel panelSouth = new JPanel(new FlowLayout());
		
		// Bucle para crear los labels y textFields
		for (String columnName : columnsNames) {

			JLabel label = new JLabel(columnName);
			labels.add(label);

			JTextField textField = new JTextField(10);
			textFields.add(textField);

			// Añadimos los labels y textFields al panel sur
			panelSouth.add(label);
			panelSouth.add(textField);
		}

		// Añadimos al panel sur el botón de guardado creado en el método a parte
		panelSouth.add(save);
		panelGroup.add(panelSouth, BorderLayout.CENTER);
		add(panels.get(1));

		// Refrescamos la ventana
		this.revalidate();
		this.repaint();
	}
}