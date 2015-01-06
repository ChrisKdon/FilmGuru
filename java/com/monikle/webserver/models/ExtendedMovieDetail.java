package com.monikle.webserver.models;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class ExtendedMovieDetail extends BasicMovieDetail {
	protected String imdbUrl;

	public ExtendedMovieDetail(String username, int movieId, String movieTitle, String moviePosterFile, String imdbId) {
		super(username, movieId, movieTitle, moviePosterFile);

		this.imdbUrl = "http://www.imdb.com/title/" + imdbId;
	}
}
