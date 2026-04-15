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
	private ArrayList<String> columnsNames = new ArrayList<>();

	public MainController() throws ClassNotFoundException, SQLException {

		data = new Data();
		connectionData = new ConnectionData();
		connectionDB = new ConnectionClass();
		window = new MainWindow(data);
		comboEvent = new ListenerCombo(this);

		createStatements();
		fillCombo();
		createTable();
		createTextFields();
		starEvents();
	}

	private void starEvents() throws ClassNotFoundException, SQLException {

		comboEvent = new ListenerCombo(this);
		window.getComboTablas().addActionListener(comboEvent);
	}

	private void fillCombo() throws SQLException {

		createResultSet(0, 0);

		while (connectionDB.getResultSets(0).next()) {

			window.getComboTablas().addItem(
					connectionDB.getResultSets(0).getString(1));
		}

		window.getComboTablas().addItem(data.getTexts().get(1));
	}

	public void createTable() throws SQLException {

		// Seleccionamos la tabla qu queremos mostrar
		String selectedTable = (String) window.getComboTablas().getSelectedItem();

		if (selectedTable != null) {

			// Creamos un resultSet para ejecutar las consultas
			ResultSet rs;
			
			/* Se ejecutará la consulta "SHOW TABLES" a no ser 
			 * que seleccionemos la opción "no_pagado" en el comboBox
			 * que se ejecutará una consulta diferente
			 */ 
			if (selectedTable != data.getTexts().get(1)) {
				
				rs = connectionDB.getStatements(1).executeQuery(
					connectionData.getSql().get(1) + selectedTable);
			}
			else {

				rs = connectionDB.getStatements(2).executeQuery(
						connectionData.getSql().get(2));
			}

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
		if (selectedTable != data.getTexts().get(1) && selectedTable != null) {

			createTextFields();
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

	/*
	 * Método para crear los ResultSets recibiendo el statement y 
	 * el numero de para seleccionar la consulta que habremos introducido 
	 * previamente en un ArrayList
	 */
	private void createResultSet(int nS, int nsql) throws SQLException {

		connectionDB.setResultSets(nS, nsql);
	}

	// Método para crear los ResultSets recibiendo el statement y la consulta sql
	private void createResultSet(int nS, String sql) throws SQLException {

		connectionDB.setResultSets(nS, sql);
	}
}