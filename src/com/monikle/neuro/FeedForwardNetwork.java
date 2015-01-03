package com.monikle.neuro;

/**
 * 3-Layer (input -> hidden -> output) neural network.
 *
 * Author:    Chris Kellendonk
 * Student #: 4810800
 * Date:      2015-01-02.
 */
public final class FeedForwardNetwork implements NeuralNetwork {


	@Override
	public void train(TrainingData trainingData) {

	}

	@Override
	public double[] run(double[] inputs) {
		return new double[0];
	}

	private double activationFunction(double input) {
		throw new UnsupportedOperationException();
	}

	private double inverseActivationFunction(double input) {
		throw new UnsupportedOperationException();
	}
}
