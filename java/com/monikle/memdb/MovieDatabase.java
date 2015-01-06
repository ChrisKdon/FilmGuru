package com.monikle.memdb;

/**
 * Singleton of the movie database.
 * <p>
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class MovieDatabase {
	private static MovieDatabase instance;

	public final RatingsTable ratings;
	public final MovieTable movies;
	public final NeuralNetTable neuralNets;
	public final MovieRaterTable raters;
	public final UserTable users;

	private MovieDatabase() {
		this.ratings = RatingsTable.getTable();
		this.movies = MovieTable.getTable();
		this.neuralNets = NeuralNetTable.getTable();
		this.raters = MovieRaterTable.getTable();
		this.users = UserTable.getTable();
	}

	public synchronized static MovieDatabase getDb() {
		if (instance == null) {
			instance = new MovieDatabase();
		}

		return instance;
	}
}
