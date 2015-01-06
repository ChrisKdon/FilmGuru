package com.monikle.webserver;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class Config {
	public static final int UPDATE_NET_MODIFICATION_COUNT = 10; 	// Update the neural net every X modifications

	public static final Map<String, Integer> NODE_INDICES = new HashMap<>(); // Node Name -> Node Index

	{
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
	}

	private static int nodeIndexCounter = 0;
	private static void mapNodeToIndex(String name) {
		NODE_INDICES.put(name.toLowerCase(), nodeIndexCounter++);
	}
}
