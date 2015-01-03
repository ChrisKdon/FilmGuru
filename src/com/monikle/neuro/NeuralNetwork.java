package com.monikle.neuro;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 * Date:      2015-01-02.
 */
public interface NeuralNetwork {
	public void train(TrainingData trainingData);
	public double[] run(double inputs[]);
}
