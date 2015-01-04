package com.monikle;

import com.monikle.memdb.MemoryDatabase;
import com.monikle.webserver.tmdb.MovieAPI;
import com.monikle.webserver.transformers.JsonTransformer;
import spark.Route;

import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.get;

public class Main {


	public static void main(String[] args) {
		MemoryDatabase memDb = new MemoryDatabase();

		initSparkServer();
	}

	private static void initSparkServer() {
		externalStaticFileLocation("./public"); // Static files

		jsonGet("/movies/random", (req, res) -> MovieAPI.popular(1));
	}

	private static void jsonGet(String path, Route route) {
		get(path, "application/json", route, new JsonTransformer());
	}
}
