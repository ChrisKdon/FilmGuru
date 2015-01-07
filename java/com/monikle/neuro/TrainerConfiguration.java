package com.monikle.neuro;

/**
 * Setup information for training a neural network.
 * <p>
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class TrainerConfiguration {
	private int maxEpochs;
	private double acceptableError;
	private TrainingData trainingData;
	private boolean shuffleTrainingData;

	private TrainerConfiguration(TrainingData trainingData) {
		this.trainingData = trainingData;
	}

	public static TrainerConfiguration create(TrainingData trainingData) {
		if (trainingData == null) {
			throw new IllegalArgumentException("`trainingData` can't be null.");
		}

		return new TrainerConfiguration(trainingData);
	}

	public int getMaxEpochs() {
		return maxEpochs;
	}

	public TrainerConfiguration setMaxEpochs(int count) {
		this.maxEpochs = count;
		return this;
	}

	public double getAcceptableError() {
		return acceptableError;
	}

	public TrainerConfiguration setAcceptableError(double error) {
		this.acceptableError = error;
		return this;
	}

	public TrainingData getTrainingData() {
		return trainingData;
	}

	public boolean getShuffleTrainingData() {
		return shuffleTrainingData;
	}

	public TrainerConfiguration setShuffleTrainingData(boolean shuffle) {
		this.shuffleTrainingData = shuffle;
		return this;
	}
}
