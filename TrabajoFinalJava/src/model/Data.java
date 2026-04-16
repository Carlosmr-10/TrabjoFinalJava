/*
 * Autor Carlos Michelena Rueda
 * Fecha: 09/11/2025
 */

package model;

import java.util.ArrayList;

public class Data {

	private ArrayList<String> texts = new ArrayList<String>();
	private ArrayList<Integer> numbers = new ArrayList<Integer>();
	
	public Data() {
		
		addTexts();
		addNumbers();
	}

	public ArrayList<String> getTexts() {
		return texts;
	}

	public void setTexts(ArrayList<String> texts) {
		this.texts = texts;
	}

	public ArrayList<Integer> getNumbers() {
		return numbers;
	}

	public void setNumbers(ArrayList<Integer> numbers) {
		this.numbers = numbers;
	}

	private void addTexts() {

		texts.add("Guardar"); // 0
		texts.add("Sin filtro"); // 1
		texts.add("Pagados"); // 2
		texts.add("No pagados"); // 3
		texts.add("-- Seleccionar --"); // 4
		texts.add("Añadir impuestos"); // 5
	}

	private void addNumbers() {

		numbers.add(3); // 0
	}
}
