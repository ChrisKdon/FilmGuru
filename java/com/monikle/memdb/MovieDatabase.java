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

	private MovieDatabase() {
		this.ratings = RatingsTable.getTable();
	}

	public synchronized static MovieDatabase getDb() {
		if (instance == null) {
			instance = new MovieDatabase();
		}

		return instance;
	}
}
