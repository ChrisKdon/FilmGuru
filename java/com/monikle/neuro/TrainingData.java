package com.monikle.neuro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Stores the data for a training session on the neural network. This
 * includes the inputs and the expected outputs.
 * <p>
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class TrainingData implements Iterable<TrainingSample> {
	private List<TrainingSample> samples;

	public TrainingData() {
		samples = Collections.synchronizedList(new ArrayList<TrainingSample>());
	}

	public void shuffle() {
		Collections.shuffle(samples);
	}

	public void add(double[] inputs, double[] outputs) {
		samples.add(new TrainingSample(inputs, outputs));
	}

	public List<TrainingSample> getSamples() {
		return samples;
	}

	@Override
	public Iterator<TrainingSample> iterator() {
		return samples.iterator();
	}
}
