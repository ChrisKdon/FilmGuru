package com.monikle;

import com.monikle.neuro.math.Matrix;

public class Main {
	public static void main(String[] args) {
		Matrix x = new Matrix(new double[][] {
				{1, 2, 3},
				{4, 5, 6},
				{7, 8, 9}
		});

		System.out.println(x);
		System.out.println();
		System.out.println(x.transpose());
	}
}
