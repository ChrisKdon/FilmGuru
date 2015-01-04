package com.monikle.neuro.math;

import java.util.Arrays;
import java.util.Random;
import java.util.function.DoubleFunction;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 * Date:      2015-01-02.
 */
public class Matrix {
	protected double[][] matrix; // Row-Column order for matrix

	public Matrix(double[][] array) {
		matrix = new double[array.length][];

		int cols = array[0].length;

		for (int i = 0; i < matrix.length; i++) {
			if (array[i].length != cols) {
				throw new RuntimeException("Invalid format all rows must have the same number of columns.");
			}

			matrix[i] = Arrays.copyOf(array[i], array[i].length);
		}
	}

	// Factories ------------------------

	/**
	 * Generate a random matrix
	 * @param rows
	 * @param columns
	 * @param min
	 * @param max
	 * @return
	 */
	public static Matrix random(int rows, int columns, double min, double max) {
		double[][] values = new double[rows][columns];

		Random rand = new Random(System.currentTimeMillis());

		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < columns; c++) {
				values[r][c] = min + (max - min) * rand.nextDouble();
			}
		}

		return new Matrix(values);
	}

	// Info -----------------------------

	public int getRowCount() {
		return matrix.length;
	}

	public int getColumnCount() {
		return matrix[0].length;
	}

	public double[] getRow(int index) {
		return Arrays.copyOf(matrix[index], matrix[index].length);
	}

	public double[] getColumn(int index) {
		double[] values = new double[getRowCount()];

		for(int i = 0; i < getColumnCount(); i++) {
			values[i] = matrix[i][index];
		}

		return values;
	}

	// Operations -----------------------

	public Matrix hadamardMultiply(Matrix withMatrix) {
		checkEqualSize(this, withMatrix);

		double[][] temp = new double[getRowCount()][getColumnCount()];
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {
				temp[row][col] = matrix[row][col] * withMatrix.matrix[row][col];
			}
		}

		return new Matrix(temp);
	}

	public Matrix scalarMultiply(double value) {
		return map(elem -> elem * value);
	}

	public Matrix transpose() {
		double[][] transposed = new double[getColumnCount()][getRowCount()];
		for (int r = 0; r < getRowCount(); r++) {
			for (int c = 0; c < getColumnCount(); c++) {
				transposed[c][r] = matrix[r][c];
			}
		}

		return new Matrix(transposed);
	}

	public Matrix multiply(Matrix withMatrix) {
		if(getColumnCount() != withMatrix.getRowCount()) {
			throw new RuntimeException("Matrices have incorrect dimensions, cannot be multiplied." +
			                           "Trying to multiply a " + getRowCount() + "x" + getColumnCount() + " by " +
																 withMatrix.getRowCount() + "x" + withMatrix.getColumnCount());
		}

		double[][] result = new double[getRowCount()][withMatrix.getColumnCount()];
		for (int r = 0; r < getRowCount(); r++) {
			for (int c = 0; c < withMatrix.getColumnCount(); c++) {
				double sum = 0;
				for (int k = 0; k < getColumnCount(); k++) {
					sum += matrix[r][k] * withMatrix.matrix[k][c];
				}

				result[r][c] = sum;
			}
		}

		return new Matrix(result);
	}

	public Matrix subtract(Matrix withMatrix) {
		checkEqualSize(this, withMatrix);

		double[][] temp = new double[getRowCount()][getColumnCount()];
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {
				temp[row][col] = matrix[row][col] - withMatrix.matrix[row][col];
			}
		}

		return new Matrix(temp);
	}

	public Matrix add(Matrix withMatrix) {
		checkEqualSize(this, withMatrix);

		double[][] temp = new double[getRowCount()][getColumnCount()];
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {
				temp[row][col] = matrix[row][col] + withMatrix.matrix[row][col];
			}
		}

		return new Matrix(temp);
	}

	public Matrix map(DoubleFunction<Double> func) {
		double[][] result = new double[getRowCount()][getColumnCount()];
		for(int r = 0; r < getRowCount(); r++) {
			for(int c = 0; c < getColumnCount(); c++) {
				result[r][c] = func.apply(matrix[r][c]);
			}
		}

		return new Matrix(result);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int r = 0; r < matrix.length; r++) {
			for (int c = 0; c < matrix[r].length; c++) {
				sb.append(matrix[r][c]);
				if (c + 1 != matrix[r].length) {
					sb.append(", ");
				}
			}
			sb.append("\n");
		}

		return sb.toString();
	}

	// Checks ---------------------------

	private static void checkEqualSize(Matrix a, Matrix b) {
		if (a.getRowCount() != b.getRowCount() || a.getColumnCount() != b.getColumnCount()) {
			throw new RuntimeException("Matrices must be the same size. A: " + a.getRowCount() + "x" + a.getColumnCount() +
																 " B: " + b.getRowCount() + "x" + b.getColumnCount());
		}
	}
}
