package com.monikle.webserver.tmdb;

import com.mashape.unirest.http.Unirest;
import com.monikle.webserver.models.ExtendedMovieDetail;
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

	private MovieAPI() {
	}

	public static ExtendedMovieDetail movie(String username, int movieId) throws Exception {
		JSONObject result = Unirest.get("http://api.themoviedb.org/3/movie/{id}")
				.queryString("api_key", API_KEY)
				.routeParam("id", Integer.toString(movieId))
				.asJson().getBody().getObject();

		return new ExtendedMovieDetail(username, movieId,
				result.getString("title"),
				result.getString("poster_path"),
				result.getString("imdb_id"));
	}

	public static List<ExtendedMovieDetail> popular(String username, int page) throws Exception {
		JSONObject result = Unirest.get("http://api.themoviedb.org/3/movie/popular")
				.queryString("api_key", API_KEY)
				.queryString("page", page)
				.asJson().getBody().getObject();

		List<ExtendedMovieDetail> movies = new ArrayList<>();

		JSONArray popularMovies = result.getJSONArray("results");
		for (int i = 0; i < popularMovies.length(); i++) {
			JSONObject m = popularMovies.getJSONObject(i);
			int movieId = m.getInt("id");
			movies.add(movie(username, movieId));
		}

		return movies;
	}
}
