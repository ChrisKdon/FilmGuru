package com.monikle.neuro;

import com.monikle.neuro.math.Vector;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 * Date:      2015-01-02.
 */
public final class TrainingSample {
	private Vector inputs, outputs;

	public TrainingSample(double[] inputs, double[] outputs) {
		this.inputs = new Vector(inputs);
		this.outputs = new Vector(outputs);
	}

	public Vector getInputs() { return inputs; }

	public Vector getOutputs() { return outputs; }
}
