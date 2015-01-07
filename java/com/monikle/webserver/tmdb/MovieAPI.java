package com.monikle.webserver.tmdb;

import com.mashape.unirest.http.Unirest;
import com.monikle.memdb.Database;
import com.monikle.models.MovieDetail;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Wrapper around the TMDB API for the functions that need to be implemented.
 *
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public class MovieAPI {
	private static Database db = Database.getDb();
	private static String API_KEY = "b49b1c4bca7553daf26632cf8237e6e6";

	private MovieAPI() {
	}

	/**
	 * Get information about a movie. This function will cache queries.
	 *
	 * @param movieId			The ID of the movie information is needed for.
	 * @return						The movie details.
	 * @throws Exception
	 */
	public static MovieDetail movie(int movieId) throws Exception {
		Optional<MovieDetail> movieOpt = db.movies.get(movieId); // Check cache

		if (!movieOpt.isPresent()) {
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
			double voteAverage = result.getDouble("vote_average");

			MovieDetail movie = new MovieDetail(movieId,
					result.getString("title"),
					result.getString("poster_path"),
					result.getString("imdb_id"),
					genres.stream().toArray(size -> new String[size]),
					year, isEnglish, voteAverage);

			db.movies.save(movie);

			return movie;
		} else {
			return movieOpt.get();
		}
	}

	/**
	 * Get the current popular movies.
	 *
	 * @param page				Page number of the popular movies to retrieve.
	 * @return						List of movie information for the popular movies.
	 * @throws Exception
	 */
	public static List<MovieDetail> popular(int page) throws Exception {
		JSONObject result = Unirest.get("http://api.themoviedb.org/3/movie/popular")
				.queryString("api_key", API_KEY)
				.queryString("page", page)
				.asJson().getBody().getObject();

		List<MovieDetail> movies = new ArrayList<>();

		JSONArray popularMovies = result.getJSONArray("results");
		for (int i = 0; i < popularMovies.length(); i++) {
			JSONObject m = popularMovies.getJSONObject(i);
			int movieId = m.getInt("id");
			movies.add(movie(movieId));
		}

		return movies;
	}
}
