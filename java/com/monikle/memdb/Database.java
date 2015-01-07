package com.monikle.memdb;

/**
 * Helper class that holds references to all the database tables.
 * <p>
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class Database {
	private static Database instance;

	public final RatingsTable ratings;
	public final MovieTable movies;
	public final MovieRaterTable raters;
	public final UserTable users;

	private Database() {
		this.ratings = RatingsTable.getTable();
		this.movies = MovieTable.getTable();
		this.raters = MovieRaterTable.getTable();
		this.users = UserTable.getTable();
	}

	public synchronized static Database getDb() {
		if (instance == null) {
			instance = new Database();
		}

		return instance;
	}
}
