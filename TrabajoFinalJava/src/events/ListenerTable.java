package events;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import controller.MainController;
import view.MainWindow;

public class ListenerTable implements TableModelListener{

	private MainController controller;
	private MainWindow window;

	public ListenerTable(MainController controller, MainWindow window) {

		this.controller = controller;
		this.window = window;
	}

	@Override
	public void tableChanged(TableModelEvent e) {

		if (e.getType() == TableModelEvent.UPDATE) {

			int row = e.getFirstRow();
			int column = e.getColumn();

			if (column != -1) {

				String nameTable = window.getComboTables().getSelectedItem().toString();
				String columnName = window.getTable().getColumnName(column);

				String pk1 = (String) window.getTable().getColumnName(0);
				boolean newText = Boolean.parseBoolean(
						window.getTable().getValueAt(row, column).toString());
				
				int pk1Value = (int) window.getTable().getValueAt(row, 0);

				if (nameTable.equalsIgnoreCase("pagos")) {

					ArrayList<Integer> values = new ArrayList<>();

					String pk2 = (String) window.getTable().getColumnName(1);
					int pk2Value = (int) window.getTable().getValueAt(row, 1);
					
					values.add(pk1Value);
					values.add(pk2Value);

//					try {
//
//						controller.updateData(nameTable, columnName, pk1, pk2, newText, values);
//					} catch (SQLException e1) {
//
//						e1.printStackTrace();
//					}
				}
				else {

//					try {
//
//						controller.updateData(nameTable, columnName, pk1, newText, pk1Value);
//					} catch (SQLException e1) {
//
//						e1.printStackTrace();
//					}
				}
			}
		}
	}
}