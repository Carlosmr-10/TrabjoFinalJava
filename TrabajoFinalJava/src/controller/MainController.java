/*
 * Autor Carlos Michelena Rueda
 * Fecha: 09/11/2025
 */

package controller;

import java.sql.SQLException;

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

	public MainController() throws ClassNotFoundException, SQLException {

		data = new Data();
		connectionData = new ConnectionData();
		connectionDB = new ConnectionClass();
		window = new MainWindow(data);
		comboEvent = new ListenerCombo(this);
	}
}
