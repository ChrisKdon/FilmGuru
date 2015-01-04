package com.monikle.neuro;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

	public List<TrainingSample> getFirst(double amount) {
		return samples.subList(0, (int)Math.floor(samples.size() * amount));
	}

	public List<TrainingSample> getLast(double amount) {
		return samples.subList(samples.size() - (int)Math.ceil(samples.size() * amount), samples.size());
	}

	@Override
	public Iterator<TrainingSample> iterator() {
		return samples.iterator();
	}
}
