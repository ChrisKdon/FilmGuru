package com.monikle.memdb;

import com.monikle.webserver.models.ExtendedMovieDetail;
import jdk.nashorn.internal.runtime.options.Option;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Stores movie information so we don't have to keep calling the web api.
 * Basically acting as a cache.
 *
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public class MovieTable {
	private static MovieTable instance;

	private Map<Integer, ExtendedMovieDetail> movies;

	private MovieTable() {
		movies = new HashMap<>();
	}

	public synchronized static MovieTable getTable() {
		if(instance == null) {
			instance = new MovieTable();
		}

		return instance;
	}

	public void save(ExtendedMovieDetail movie) {
		movies.put(movie.getMovieId(), movie);
	}

	public Optional<ExtendedMovieDetail> get(int movieId) {
		return Optional.ofNullable(movies.get(movieId));
	}
}
