package model;

import model.interfaces.Die;

import java.util.Objects;

public class DieImpl implements Die {
	private int number;
	private int value;
	private int numFaces;
	private static final int NUM_FACES = 6;

	public DieImpl(int number, int value, int numFaces) throws IllegalArgumentException {
		if (number == 1 || number == 2)
			this.number = number;
		if (value <= NUM_FACES)
			this.value = value;
		if (numFaces >= 1)
			this.numFaces = numFaces;
	}

	@Override
	public int getNumber() {
		return number;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public int getNumFaces() {
		return numFaces;
	}

	@Override
	public boolean equals(Die die) {
		return this.hashCode() == die.hashCode();
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.value);
	}
	
	@Override
	public String toString() {
		String value = null;
		switch (getValue()) {
		case 1:
			value = "One";
			break;
		case 2:
			value = "Two";
			break;
		case 3:
			value = "Three";
			break;
		case 4:
			value = "Four";
			break;
		case 5:
			value = "Five";
			break;
		case 6:
			value = "Six";
			break;
		}
		return value;
	}
}
