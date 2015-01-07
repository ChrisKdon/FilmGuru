package com.monikle.neuro;

/**
 * Information about how the training of the neural network went.
 * <p>
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class TrainingResult {
	private double error;
	private int epochs;

	public TrainingResult(int epochs, double error) {
		this.epochs = epochs;
		this.error = error;
	}

	public double getError() {
		return error;
	}

	public int getEpochs() {
		return epochs;
	}

	@Override
	public String toString() {
		return "Epochs: " + getEpochs() + ", Error: " + error;
	}
}
