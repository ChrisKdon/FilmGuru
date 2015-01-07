package com.monikle.neuro;

import com.monikle.neuro.math.Vector;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public interface NeuralNetwork {
	/**
	 * Train the network.
	 * @param config
	 * @return The error.
	 */
	public TrainingResult train(TrainerConfiguration config);

	/**
	 * Run the network using these values as inputs.
	 * @param inputs The input values.
	 * @return The output vector of the neural network.
	 */
	public Vector run(double... inputs);
	public Vector run(Vector inputs);

	/**
	 * Run multiple input vectors.
	 */
	public Vector[] runMultiple(Vector[] inputs);
}
