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
 */
public final class FeedForwardNetwork implements NeuralNetwork {
	private final int inputCount;                 // The number of inputs
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

		if (momentum < 0 || momentum > 1) {
			throw new IllegalArgumentException("Range Exception: 0 < momentum < 1.");
		}

		this.inputCount = inputCount;
		this.learningRate = learningRate;
		this.momentum = momentum;

		this.inputToHiddenWeights = Matrix.random(inputCount, hiddenCount, -1, 1);
		this.hiddenToOutputWeights = Matrix.random(hiddenCount, outputCount, -1, 1);
	}

	@Override
	public TrainingResult train(TrainerConfiguration config) {
		if (config.getShuffleTrainingData()) {
			config.getTrainingData().shuffle();
		}

		// Prepare Sample Training Data
		final List<TrainingSample> trainingData = config.getTrainingData().getSamples();
		Vector[] validationInputs = trainingData.parallelStream().map(s -> s.getInputs()).toArray(size -> new Vector[size]);
		Vector[] validationOutputs = trainingData.parallelStream().map(s -> s.getOutputs()).toArray(size -> new Vector[size]);

		// Run Training
		double epochError = 0;
		for (int epoch = 0; epoch < config.getMaxEpochs(); epoch++) {
			Collections.shuffle(trainingData);

			for (TrainingSample sample : trainingData) {
				// Get Weights
				final Matrix inputWeights = inputToHiddenWeights;
				final Matrix hiddenWeights = hiddenToOutputWeights;

				// Calculate Output
				Vector actual = run(sample.getInputs());

				// Calculate Deltas
				Vector deltaOutput = Vector.fromMatrix(sample.getOutputs().subtract(actual));
				Vector deltaHidden = Vector.fromMatrix(deltaOutput.multiply(hiddenWeights.transpose()));

				// Calculate New Weights
				Vector inputWithBias = sample.getInputs();
				this.inputToHiddenWeights = backPropWeights(inputWithBias, inputWeights, deltaHidden);
				Vector newHidden = calculateLayerOutput(inputWithBias, inputWeights);
				this.hiddenToOutputWeights = backPropWeights(newHidden, hiddenWeights, deltaOutput);
			}

			// Calculate Error
			epochError = computeEpochError(validationOutputs, runMultiple(validationInputs));
			if (epochError <= config.getAcceptableError()) { // Within acceptable error bounds
				return new TrainingResult(epoch, epochError);
			}
		}

		return new TrainingResult(config.getMaxEpochs(), epochError);
	}

	private Matrix backPropWeights(Vector input, Matrix weights, Vector delta) {
		Matrix startWeights = weights;

		Vector a = Vector.fromMatrix(input.multiply(weights).map(this::derivativeActivationFunction));
		Vector b = Vector.fromMatrix(a.hadamardMultiply(delta));
		Vector c = Vector.fromMatrix(b.scalarMultiply(learningRate));
		Matrix d = c.transpose().multiply(input);
		Matrix newWeights = d.transpose().add(weights);

		// Momentum
		Matrix momentumWeights = newWeights.subtract(startWeights);
		newWeights = momentumWeights.scalarMultiply(momentum).add(startWeights);

		return newWeights;
	}

	private double computeEpochError(Vector[] expected, Vector[] actual) {
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

		Vector hidden = calculateLayerOutput(input, inputToHiddenWeights);
		Vector output = calculateLayerOutput(hidden, hiddenToOutputWeights);

		return output;
	}

	@Override
	public Vector run(double... inputs) {
		return run(new Vector(inputs));
	}

	/**
	 * Adds the bias to the input and calculates the output.
	 *
	 * @param input
	 * @param weights
	 * @return
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
