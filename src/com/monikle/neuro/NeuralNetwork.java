package com.monikle.neuro;

import com.monikle.neuro.math.Vector;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 * Date:      2015-01-02.
 */
public interface NeuralNetwork {
	public void train(TrainerConfiguration config);
	public Vector run(double... inputs);
	public Vector run(Vector inputs);
	public Vector[] runMultiple(Vector[] inputs);
}
