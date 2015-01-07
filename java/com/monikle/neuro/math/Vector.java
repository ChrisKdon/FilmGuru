package com.monikle.neuro.math;

import java.util.Arrays;

/**
 * A one dimensional matrix.
 *
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class Vector extends Matrix {
	private int length;

	public Vector(double[] array) {
		super(new double[][]{array});

		length = array.length;
	}

	public static Vector fromMatrix(Matrix m) {
		if(m.getRowCount() == 1) {
			return new Vector(m.getRow(0));
		} else if(m.getColumnCount() == 1) {
			return new Vector(m.getColumn(0));
		}

		throw new RuntimeException("Matrix must be 1xn or nx1.");
	}

	public Vector add(double value) {
		Vector base = new Vector(getValues());

		double[] newVector = base.getRow(0);
		newVector = Arrays.copyOf(newVector, newVector.length + 1);
		newVector[newVector.length - 1] = value;

		return new Vector(newVector);
	}

	public int getLength() {
		return length;
	}

	public double[] getValues() {
		return this.getRow(0);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		double[] values = getValues();

		sb.append("[");
		for(int i = 0; i < values.length; i++) {
			sb.append(values[i]);
			if(i + 1 != values.length) { sb.append(", "); }
		}
		sb.append("]");

		return sb.toString();
	}
}
