package com.monikle;

import com.monikle.memdb.MovieDatabase;
import com.monikle.webserver.Config;
import com.monikle.webserver.tmdb.MovieAPI;
import com.monikle.webserver.transformers.JsonTransformer;
import spark.Route;

import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.get;

public class Main {
	public static void main(String[] args) {
		initSparkServer();
	}

	private static void initSparkServer() {
		externalStaticFileLocation("./public"); // Static files

		String DEBUG_USERNAME = "test";

		jsonGet("/movies/popular/:page", (req, res) -> MovieAPI.popular(DEBUG_USERNAME, Integer.parseInt(req.params("page"))));
		get("/users/rate/movie/:id", (req, res) -> {
			MovieDatabase db = MovieDatabase.getDb();

			int movieId = Integer.parseInt(req.params("id"));
			int rating = Integer.parseInt(req.queryParams("rating"));

			db.setRating(DEBUG_USERNAME, movieId, rating); // Update rating

			long modCount = db.modificationCount(DEBUG_USERNAME);
			if(modCount > 0 && modCount % Config.UPDATE_NET_MODIFICATION_COUNT == 0) {
				// TODO: Train Neural Network
			}

			return "ok";
		});
	}

	private static void jsonGet(String path, Route route) {
		get(path, "application/json", route, new JsonTransformer());
	}
}
