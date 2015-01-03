package com.monikle.neuro.math;

import java.util.Arrays;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 * Date:      2015-01-02.
 */
public class Matrix {
	private double[][] matrix; // Row-Column order for matrix

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

	public int getRows() {
		return matrix.length;
	}

	public int getColumns() {
		return matrix[0].length;
	}

	public Matrix hadamardProduct(Matrix withMatrix) {
		checkSize(this, withMatrix);

		double[][] temp = new double[getRows()][getColumns()];

		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {
				temp[row][col] = matrix[row][col] * withMatrix.matrix[row][col];
			}
		}

		return new Matrix(temp);
	}

	public Matrix transpose() {
		double[][] transposed = new double[getColumns()][getRows()];

		for(int r = 0; r < matrix.length; r++) {
			for(int c = 0; c < matrix[r].length; c++) {
				transposed[c][r] = matrix[r][c];
			}
		}

		return new Matrix(transposed);
	}

	public Matrix multiply(Matrix withMatrix) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for(int r = 0; r < matrix.length; r++) {
			for(int c = 0; c < matrix[r].length; c++) {
				sb.append(matrix[r][c]);
				if(c + 1 != matrix[r].length) {
					sb.append(", ");
				}
			}
			sb.append("\n");
		}

		return sb.toString();
	}

	private static void checkSize(Matrix a, Matrix b) {
		if(a.getRows() != b.getRows() || a.getColumns() != b.getColumns()) {
			throw new RuntimeException("Matrices must be the same size.");
		}
	}
}
