package com.monikle.neuro;

import com.monikle.neuro.math.Matrix;
import com.monikle.neuro.math.Vector;

import java.util.Collections;
import java.util.List;

/**
 * 3-Layer (input -> hidden -> output) neural network.
 * <p>
 * Author:    Chris Kellendonk
 * Student #: 4810800
 * Date:      2015-01-02.
 */
public final class FeedForwardNetwork implements NeuralNetwork {
	private final int inputCount;                // The number of inputs
	private final double learningRate, momentum;  // Network training settings
	private Matrix inputToHiddenWeights, hiddenToOutputWeights;

	public FeedForwardNetwork(int inputCount, int hiddenCount, int outputCount, double learningRate, double momentum) {
		if (inputCount <= 0) {
			throw new IllegalArgumentException("`inputCount` must be > 0");
		}
		if (hiddenCount <= 0) {
			throw new IllegalArgumentException("`hiddenCount` must be > 0");
		}
		if (outputCount <= 0) {
			throw new IllegalArgumentException("`outputCount` must be > 0");
		}

		this.inputCount = inputCount;
		this.learningRate = learningRate;
		this.momentum = momentum;

		this.inputToHiddenWeights = Matrix.random(inputCount + 1, hiddenCount, -1, 1);
		this.hiddenToOutputWeights = Matrix.random(hiddenCount + 1, outputCount, -1, 1);
	}

	@Override
	public void train(TrainerConfiguration config) {
		// Prepare Sample Training Data
		final List<TrainingSample> trainingData = config.getTrainingData().getFirst(1 - config.getValidationAmount());

		List<TrainingSample> validationData = config.getTrainingData().getLast(config.getValidationAmount());
		Vector[] validationInputs = validationData.parallelStream().map(s -> s.getInputs()).toArray(size -> new Vector[size]);
		Vector[] validationOutputs = validationData.parallelStream().map(s -> s.getOutputs()).toArray(size -> new Vector[size]);

		// Run Training
		for (int epoch = 0; epoch < config.getMaxEpochs(); epoch++) {
			Collections.shuffle(trainingData);

			for (TrainingSample sample : trainingData) {
				Vector actual = run(sample.getInputs());

				Vector deltaOutput = Vector.fromMatrix(sample.getOutputs().subtract(actual));
				Vector deltaHidden = Vector.fromMatrix(deltaOutput.multiply(hiddenToOutputWeights.transpose()));
			}

			// Calculate Error
			double epochError = computeEpochError(validationOutputs, runMultiple(validationInputs));
			if (epochError <= config.getAcceptableError()) { // Within acceptable error bounds
				return;
			}
		}
	}

	protected double computeEpochError(Vector[] expected, Vector[] actual) {
		double error = 0;

		for (int i = 0; i < expected.length; i++) {
			double[] expectedValues = expected[i].getValues();
			double[] actualValues = actual[i].getValues();

			double norm = 0;
			for (int x = 0; x < expectedValues.length; x++) {
				double t = actualValues[x] - expectedValues[x];
				norm += t * t;
			}
			error += norm;
		}

		error *= 0.5;

		return error;
	}

	@Override
	public Vector[] runMultiple(Vector[] inputs) {
		Vector[] results = new Vector[inputs.length];
		for (int i = 0; i < inputs.length; i++) {
			results[i] = run(inputs[i]);
		}

		return results;
	}

	@Override
	public Vector run(Vector input) {
		if (input.getLength() != inputCount) {
			throw new IllegalArgumentException("`input` vector must be of length " + inputCount);
		}

		Vector hidden = calculateLayerOutput(input.add(1), inputToHiddenWeights);
		Vector output = calculateLayerOutput(hidden.add(1), hiddenToOutputWeights);

		return output;
	}

	@Override
	public Vector run(double... inputs) {
		return run(new Vector(inputs));
	}

	/**
	 * @param input Input vector with bias already in it.
	 */
	public Vector calculateLayerOutput(Vector input, Matrix weights) {
		return Vector.fromMatrix(input.multiply(weights).map(this::activationFunction));
	}

	/**
	 * Sigmoid function.
	 *
	 * @param input
	 * @return
	 */
	private double activationFunction(double input) {
		return 1.0 / (1.0 + Math.exp(-input));
	}

	private double derivativeActivationFunction(double input) {
		return activationFunction(input) * (1 - activationFunction(input));
	}
}
