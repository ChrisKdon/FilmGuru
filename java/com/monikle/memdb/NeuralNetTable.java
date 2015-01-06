package com.monikle.memdb;

import com.monikle.neuro.NeuralNetwork;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class NeuralNetTable {
	private static NeuralNetTable instance;

	private Map<String, NeuralNetwork> networks;  // <Username, Neural Network>

	private NeuralNetTable() {
		networks = new HashMap<>();
	}

	public synchronized static NeuralNetTable getTable() {
		if (instance == null) {
			instance = new NeuralNetTable();
		}

		return instance;
	}

	public void save(String username, NeuralNetwork network) {
		networks.put(username, network);
	}

	public Optional<NeuralNetwork> get(String username) {
		return Optional.ofNullable(networks.get(username));
	}
}
