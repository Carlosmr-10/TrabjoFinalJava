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
		texts.add("no_pagados"); // 1
	}

	private void addNumbers() {

		numbers.add(0); // 0
	}
}
