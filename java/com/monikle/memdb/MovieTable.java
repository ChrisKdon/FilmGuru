package com.monikle.memdb;

import com.monikle.models.MovieDetail;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Stores movie information so we don't have to keep calling the web api.
 * Basically acting as a cache.
 * <p>
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public class MovieTable {
	private static MovieTable instance;

	private Map<Integer, MovieDetail> movies;

	private MovieTable() {
		movies = new HashMap<>();
	}

	public synchronized static MovieTable getTable() {
		if (instance == null) {
			instance = new MovieTable();
		}

		return instance;
	}

	public void save(MovieDetail movie) {
		movies.put(movie.getMovieId(), movie);
	}

	public Optional<MovieDetail> get(int movieId) {
		return Optional.ofNullable(movies.get(movieId));
	}
}
