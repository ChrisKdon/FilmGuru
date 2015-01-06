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
	public Vector run(double... inputs);
	public Vector run(Vector inputs);
	public Vector[] runMultiple(Vector[] inputs);
}
