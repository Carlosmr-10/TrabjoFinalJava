package events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.MainController;
import view.MainWindow;

public class ListenerButton implements ActionListener {

	private MainController controller;
	private MainWindow window;

	public ListenerButton(MainController controller, MainWindow window) {

		this.controller = controller;
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		boolean insert = false;
		int size = window.getTextFields().size();

		for (int i = 0; i < size; i++) {

			if (!window.getTextFields().get(i).getText().isEmpty()) {

				insert = true;
			}
		}

		if (insert) {

			finalInsert(size);
		}
		else {

			System.out.println("No hay nada que añadir");
		}
	}

	private void finalInsert(int size) {

		String nameTable = window.getComboTables().getSelectedItem().toString();
		ArrayList<String> data = getInsertData(size);

		try {

			controller.insertData(nameTable, data);
		}
		catch (SQLException e1) {

			e1.printStackTrace();
		}

		System.out.println("Campos añadidos");
	}

	private ArrayList<String> getInsertData(int size){

		ArrayList<String> data = new ArrayList<>();

		for (int i = 0; i < size; i++) {

			data.add(window.getTextFields().get(i).getText());
		}

		return data;
	}
}
