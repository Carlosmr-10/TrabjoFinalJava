/*
 * Autor Carlos Michelena Rueda
 * Fecha: 09/11/2025
 */

package controller;

import java.sql.*;
import java.util.ArrayList;

import connection.ConnectionClass;
import model.*;
import view.MainWindow;
import events.*;

public class MainController {

	private Data data;
	private ConnectionData connectionData;
	private ConnectionClass connectionDB;
	private MainWindow window;
	private ListenerCombo comboEvent;
	private ListenerButton buttonEvent;
//	private ListenerTable tableEvent;
	private ArrayList<String> columnsNames = new ArrayList<>();

	public MainController() throws ClassNotFoundException, SQLException {

		data = new Data();
		connectionData = new ConnectionData();
		connectionDB = new ConnectionClass();
		window = new MainWindow(data);
		comboEvent = new ListenerCombo(this);
		buttonEvent = new ListenerButton(this, window);

		createStatements();
		fillCombo();
		startEvents();
	}

	// Añadir listeners a las variables
	private void startEvents() throws ClassNotFoundException, SQLException {

		window.getComboTables().addActionListener(comboEvent);

		window.getSave().addActionListener(buttonEvent);
		
		for (int i = 0; i < window.getRadioButtons().size(); i++) {

			window.getRadioButtons().get(i).addActionListener(buttonEvent);
		}

		window.getCheck().addActionListener(buttonEvent);
	
//		tableEvent = new ListenerTable(this, window);
//		window.getTable().getModel().addTableModelListener(tableEvent);
	}

	/*
	 * Método para crear los ResultSets recibiendo el statement y 
	 * el numero de para seleccionar la consulta que habremos introducido 
	 * previamente en un ArrayList
	 */
	private void createResultSet(int nS, int nsql) throws SQLException {

		connectionDB.setResultSets(nS, nsql);
	}

//	// Método para crear los ResultSets recibiendo el statement y la consulta sql
//	private void createResultSet(int nS, String sql) throws SQLException {
//
//		connectionDB.setResultSets(nS, sql);
//	}

	// Rellenar los datos del comboBox
	private void fillCombo() throws SQLException {

		window.getComboTables().addItem(data.getTexts().get(4));

		// Creacion del resultSet para mostrar todas las tablas de la base de datos
		createResultSet(0, 0);

		while (connectionDB.getResultSets(0).next()) {

			window.getComboTables().addItem(
					connectionDB.getResultSets(0).getString(1));
		}
	}

	// Opcion del RadioButton para no aplicar ningun tipo de filtro
	public void noFilter() throws SQLException {

		// Seleccionamos la tabla que queremos mostrar
		String selectedTable = (String) window.getComboTables().getSelectedItem();

		if (selectedTable != data.getTexts().get(4)) {

			// Creamos un resultSet para ejecutar las consultas
			ResultSet rs = connectionDB.getStatements(1).executeQuery(
					connectionData.getSql().get(1) + selectedTable);
			
			createTable(rs);
			createTextFields();
		}
	}

	// Seleccionamos el filtro para aquellos recibos que estan pagados
	public void notPaidFilter() throws SQLException {

		ResultSet rs = connectionDB.getStatements(2).executeQuery(
				connectionData.getSql().get(2));
		
		createTable(rs);
	}

	// Seleccionamos el filtro para aquellos recibos que estan pagados
	public void paidFilter() throws SQLException {

		ResultSet rs = connectionDB.getStatements(3).executeQuery(
				connectionData.getSql().get(3));
		
		createTable(rs);
	}

	private void createTable(ResultSet rs) throws SQLException {
		// Cogemos los datos que se hayan guardado en el resultSet
		ResultSetMetaData metaData = rs.getMetaData();
		// Contamos las columnas y las guardamos en una variable
		int numberOfColumns = metaData.getColumnCount();
		
		// Limpiamos el arraylist para asegurarnos de que no interfiera al cambiar de tabla
		columnsNames.clear();

		// Creamos un bucle for para añadir los nombres de las columnas al arraylist que los contiene
		for (int i = 1; i <= numberOfColumns; i++) {

			columnsNames.add(metaData.getColumnName(i));
		}

		// Llamada al metodo para crear la tabla
		window.createPanelCenter(numberOfColumns, columnsNames);

		// Comprobamos con un while que haya más filas en la tabla
		while (rs.next()) {

			// Creamos un array de tipo objeto que almacenará los datos que hay por fila
			Object[] row = new Object [numberOfColumns];

			// Bucle for para rellenar la tabla
			for (int i = 0; i < numberOfColumns; i++) {

				row[i] = rs.getObject(i + 1);
			}
			window.getTableDB().addRow(row);
		}
	}

	// Creación de los labels y textFields de la vista para llamarlo posteriormente
	private void createTextFields() {

		window.createPanelSouth(columnsNames);
	}

	// Método para crear los Statements
	private void createStatements() throws SQLException {

		// Aqui se creara un statement por consulta sql que haya en la clase ConnectionData
		for (int i = 0; i < connectionData.getSql().size(); i++) {

			connectionDB.setStatements();
		}
	}

	// Método para insertar los datos que recibe el nombre de la tabla
	// y los datos que se van a insertar en ella
	public void insertData(String tableName, ArrayList<String> data) throws SQLException {

		// Creamos el inicio del insert
		String sql = connectionData.insertData(tableName);
		int size = data.size();

		/* Usamos un bucle for para añadir la cantidad exacta de "?" como datos
		 * se van a añadir, que mas tarde se sustituirá por los datos
		 * por los datos que introduzcamos en los textFields
		 */
		for (int i = 0; i < size; i++) {

			if (i == size - 1) {

				sql += "?)";
			}
			else {

				sql += "?, ";
			}
		}

		// Llamada al método para generar el prepared statement
		generatePreparedStatement(sql, size, data);
	}

	// Método que genera el prepared statement del insert
	private void generatePreparedStatement(String sql, int size, ArrayList<String> data) throws SQLException {

		try (PreparedStatement ps = connectionDB.getConnectionDB().prepareStatement(sql)) {

			for (int i = 0; i < size; i++) {

				ps.setString(i + 1, data.get(i));
			}

			ps.executeUpdate();
		}
	}

	// Médoto que activa la opción de aumentar el importe un 10%
	public void updateData() throws SQLException {

		String sql = connectionData.getSql().get(4);

		try (PreparedStatement ps = connectionDB.getConnectionDB().prepareStatement(sql)) {

			ps.executeUpdate();
		}
	}

//	public void updateData(String tableName, String columnName, String idColumn,
//			String newText, int idValue) throws SQLException {
//
//		String sql = connectionData.updateData(tableName, columnName, idColumn);
//
//		try (PreparedStatement ps = connectionDB.getConnectionDB().prepareStatement(sql)) {
//
//			ps.setString(1, newText);
//			ps.setString(2, idValue);
//
//			ps.executeUpdate();
//		}
//	}

//	public void updateData(String tableName, String columnName, String idColumn, String idColumn2,
//			boolean newText, ArrayList<Integer> values) throws SQLException {
//
//		String sql = "UPDATE " + tableName + " SET " + columnName
//				+ " = ? WHERE " + idColumn + " = ? AND " + idColumn2 + " = ?";
//
//		System.out.println(sql);
//		try (PreparedStatement ps = connectionDB.getConnectionDB().prepareStatement(sql)) {
//
//			ps.setBoolean(1, newText);
//
//			for (int i = 0; i < values.size(); i++) {
//				
//				ps.setInt(i + 2, values.get(i));
//			}
//			System.out.println(ps);
//			ps.executeUpdate();
//		}
//	}
}