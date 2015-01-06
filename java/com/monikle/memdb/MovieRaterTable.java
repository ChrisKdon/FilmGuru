package com.monikle.memdb;

import com.monikle.webserver.rater.MovieRater;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public class MovieRaterTable {
	private static MovieRaterTable instance;

	private Map<String, MovieRater> raters;  // <Username, Movie Rater>

	private MovieRaterTable() {
		raters = new HashMap<>();
	}

	public synchronized static MovieRaterTable getTable() {
		if (instance == null) {
			instance = new MovieRaterTable();
		}

		return instance;
	}

	public void save(String username, MovieRater rater) {
		raters.put(username, rater);
	}

	public Optional<MovieRater> get(String username) {
		return Optional.ofNullable(raters.get(username));
	}
}
