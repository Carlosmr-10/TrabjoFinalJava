/*
 * Autor Carlos Michelena Rueda
 * Fecha: 09/11/2025
 */

package events;

import java.awt.event.*;
import java.sql.SQLException;

import controller.MainController;

// Evento del comboBox para poder seleccionar las opciones que contiene
public class ListenerCombo implements ActionListener {

	private MainController controller;

	public ListenerCombo(MainController controller) throws ClassNotFoundException, SQLException {

		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {

			controller.noFilter();
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}