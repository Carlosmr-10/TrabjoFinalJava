package events;

import java.awt.event.*;
import java.sql.SQLException;

import controller.MainController;

public class ListenerCombo implements ActionListener {

	private MainController controller;

	public ListenerCombo(MainController controller) throws ClassNotFoundException, SQLException {

		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {

			controller.createTable();
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}