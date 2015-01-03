package com.monikle.neuro;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Stores the data for a training session on the neural network. This
 * includes the inputs and the expected outputs.
 *
 * Author:    Chris Kellendonk
 * Student #: 4810800
 * Date:      2015-01-02.
 */
public final class TrainingData implements Iterable<TrainingSample> {
	private ArrayList<TrainingSample> samples;

	@Override
	public Iterator<TrainingSample> iterator() {
		return samples.iterator();
	}
}
