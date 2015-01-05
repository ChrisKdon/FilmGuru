package com.monikle.webserver.tmdb;

import com.mashape.unirest.http.Unirest;
import com.monikle.memdb.MovieDatabase;
import com.monikle.webserver.models.Movie;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public class MovieAPI {
	private static String API_KEY = "b49b1c4bca7553daf26632cf8237e6e6";
	private static MovieDatabase db = MovieDatabase.getDb();

	private MovieAPI() {}

	public static List<Movie> popular(String username, int page) throws Exception {
		JSONObject result = Unirest.get("http://api.themoviedb.org/3/movie/popular")
				.queryString("api_key", API_KEY)
				.queryString("page", page)
				.asJson().getBody().getObject();

		List<Movie> movies = new ArrayList<>();


		JSONArray popularMovies = result.getJSONArray("results");
		for(int i = 0; i < popularMovies.length(); i++) {
			JSONObject m = popularMovies.getJSONObject(i);

			int movieId = m.getInt("id");
			int rating = db.getRatingOrDefault(username, movieId, -1);
			boolean hasUserRating = rating >= 0;

			if(rating < 0) {
				hasUserRating = false;
				// TODO: Calculate from neural network
			}

			movies.add(new Movie(movieId,
													 m.getString("title"),
													 m.getString("poster_path"),
													 rating, hasUserRating));
		}

		return movies;
	}
}
