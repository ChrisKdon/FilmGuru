package com.monikle.webserver.rater;

import com.monikle.memdb.MovieRaterTable;
import com.monikle.webserver.Config;

import java.util.Optional;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class MovieRaterFactory {
	private static final MovieRaterTable raterTable = MovieRaterTable.getTable();

	private MovieRaterFactory(){}

	public synchronized static MovieRater getForUsername(String username) {
		Optional<MovieRater> optRater = raterTable.get(username);

		if(optRater.isPresent()) {
			return optRater.get();
		} else {
			MovieRater rater = new MovieRater(username, Config.MAX_RATING,
					Config.HIDDEN_NODE_COUNT, Config.LEARNING_RATE, Config.MOMENTUM);

			raterTable.save(username, rater);
			return rater;
		}
	}
}
