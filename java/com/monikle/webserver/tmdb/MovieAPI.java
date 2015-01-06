package com.monikle.webserver.tmdb;

import com.mashape.unirest.http.Unirest;
import com.monikle.memdb.MovieDatabase;
import com.monikle.webserver.models.ExtendedMovieDetail;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public class MovieAPI {
	private static MovieDatabase db = MovieDatabase.getDb();
	private static String API_KEY = "b49b1c4bca7553daf26632cf8237e6e6";

	private MovieAPI() {}

	public static ExtendedMovieDetail movie(String username, int movieId) throws Exception {
		Optional<ExtendedMovieDetail> movieOpt = db.movies.get(movieId); // Check cache

		if(!movieOpt.isPresent()) {
			JSONObject result = Unirest.get("http://api.themoviedb.org/3/movie/{id}")
					.queryString("api_key", API_KEY)
					.routeParam("id", Integer.toString(movieId))
					.asJson().getBody().getObject();

			List<String> genres = new ArrayList<>(20);
			JSONArray genreArray = result.getJSONArray("genres");
			for (int i = 0; i < genreArray.length(); i++) {
				genres.add(genreArray.getJSONObject(i).getString("name"));
			}

			int year = Integer.parseInt(result.getString("release_date").substring(0, 3));
			boolean isEnglish = result.getString("original_language").equals("en");

			ExtendedMovieDetail movie = new ExtendedMovieDetail(username, movieId,
					result.getString("title"),
					result.getString("poster_path"),
					result.getString("imdb_id"),
					genres.stream().toArray(size -> new String[size]),
					year, isEnglish);

			db.movies.save(movie);

			return movie;
		} else {
			return movieOpt.get();
		}
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
