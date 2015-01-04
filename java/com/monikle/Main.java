package com.monikle;

import com.monikle.memdb.MemoryDatabase;
import com.monikle.webserver.models.Movie;
import com.monikle.webserver.transformers.JsonTransformer;
import spark.Route;

import java.util.ArrayList;

import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.get;

public class Main {
	public static void main(String[] args) {
		MemoryDatabase memDb = new MemoryDatabase();

		initSparkServer();
	}

	private static void initSparkServer() {
		externalStaticFileLocation("./public"); // Static files

		jsonGet("/movies/random",(req, res) -> {
			ArrayList<Movie> movies = new ArrayList<Movie>();
			movies.add(new Movie("550", "Fight Club", "2lECpi35Hnbpa4y46JX0aY3AWTy.jpg", 3, true));
			movies.add(new Movie("550", "Fight Club 2", "2lECpi35Hnbpa4y46JX0aY3AWTy.jpg", 1, false));
			return movies;
		});
	}

	private static void jsonGet(String path, Route route) {
		get(path, "application/json", route, new JsonTransformer());
	}
}
