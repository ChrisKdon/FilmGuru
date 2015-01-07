package com.monikle.webserver;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration file for network parameters.
 *
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class Config {
	public static final int UPDATE_NET_MODIFICATION_COUNT = 10; 	// Update the neural net every X modifications

	// Rater Params
	public static final int MAX_RATING = 5;
	public static final int MIN_YEAR = 1950;
	public static final int MAX_YEAR = 2100;
	public static final int MIN_VOTE = 1;
	public static final int MAX_VOTE = 10;

	// Neural Network Params
	public static final double ACCEPTABLE_ERROR = 0.3;
	public static final int MAX_EPOCHS = 2000;
	public static final int MAX_RETRIES = 3;
	public static final int HIDDEN_NODE_COUNT = 100;
	public static final double LEARNING_RATE = 0.1;
	public static final double MOMENTUM = 0.5;

	public static final Map<String, Integer> NODE_INDICES = new HashMap<>(); // Node Name -> Node Index

	static {
		// Movie Genres
		mapNodeToIndex("Action");
		mapNodeToIndex("Adventure");
		mapNodeToIndex("Animation");
		mapNodeToIndex("Comedy");
		mapNodeToIndex("Crime");
		mapNodeToIndex("Documentary");
		mapNodeToIndex("Drama");
		mapNodeToIndex("Family");
		mapNodeToIndex("Fantasy");
		mapNodeToIndex("Foreign");
		mapNodeToIndex("History");
		mapNodeToIndex("Horror");
		mapNodeToIndex("Music");
		mapNodeToIndex("Mystery");
		mapNodeToIndex("Romance");
		mapNodeToIndex("Science Fiction");
		mapNodeToIndex("TV Movie");
		mapNodeToIndex("Thriller");
		mapNodeToIndex("War");
		mapNodeToIndex("Western");

		// Other
		mapNodeToIndex("Year");
		mapNodeToIndex("English");
		mapNodeToIndex("VoteAverage");
	}

	private static int nodeIndexCounter = 0;
	private static void mapNodeToIndex(String name) {
		NODE_INDICES.put(name, nodeIndexCounter++);
	}
}
