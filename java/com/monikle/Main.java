package com.monikle;

import com.monikle.memdb.Database;
import com.monikle.memdb.UserTable;
import com.monikle.models.MovieDetail;
import com.monikle.neuro.NeuralNetwork;
import com.monikle.webserver.Config;
import com.monikle.webserver.rater.MovieRaterFactory;
import com.monikle.webserver.tmdb.MovieAPI;
import com.monikle.webserver.transformers.JsonTransformer;
import com.monikle.webserver.viewmodels.MovieViewModel;
import spark.Response;

import java.util.List;

import static spark.Spark.*;

public class Main {
	public static void main(String[] args) {
		initSparkServer();
	}

	private static void initSparkServer() {
		externalStaticFileLocation("./public"); // Static files

		/**
		 * Redirect to either login or the main app
		 */
		get("/", (req, res) -> {
			if (req.session().attribute("username") == null) {
				res.redirect("/login.html");
			} else {
				res.redirect("/app.html");
			}

			return null;
		});

		/**
		 * Log a user out.
		 */
		post("/logout", (req, res) -> {
			req.session().removeAttribute("username");
			res.redirect("/login.html");
			return null;
		});

		/**
		 * Log a user in.
		 */
		post("/userlogin", (req, res) -> {
			UserTable users = UserTable.getTable();

			String[] parts = req.body().split("&");
			String username = parts[0].replace("username=", "").trim();
			String password = parts[1].replace("password=", "").trim();

			String error = null;
			if (username.length() <= 0 || password.length() <= 0) {
				error = "incomplete";
			} else if (users.exists(username) && !users.login(username, password)) {
				error = "wrongpass";
			}

			if (error != null) {
				res.redirect("/login.html?error=" + error);
				return null;
			}

			users.save(username, password);
			req.session().attribute("username", username);
			res.redirect("/");

			return null;
		});

		/**
		 * Get the popular movies
		 */
		get("/movies/popular/:page", "application/json", (req, res) -> {
			if (!gaurdUsername(req, res)) {
				return "error";
			}

			String username = req.session().attribute("username");

			List<MovieDetail> popular = MovieAPI.popular(Integer.parseInt(req.params("page")));
			return popular.parallelStream().map(movie -> new MovieViewModel(username, movie)).toArray();
		}, new JsonTransformer());

		/**
		 * Rate a movie
		 */
		get("/users/rate/movie/:id", (req, res) -> {
			if (!gaurdUsername(req, res)) {
				return "error";
			}

			Database db = Database.getDb();
			String username = req.session().attribute("username");

			int movieId = Integer.parseInt(req.params("id"));
			int rating = Integer.parseInt(req.queryParams("rating"));

			db.ratings.save(username, movieId, rating); // Update rating

			// Train the rater
			long modCount = db.ratings.modificationCount(username);
			if (modCount > 0 && modCount % Config.UPDATE_NET_MODIFICATION_COUNT == 0) {
				MovieRaterFactory.getForUsername(username).train();
			}

			return "done";
		});
	}

	/**
	 * Ensure the session has a username.
	 * @return true if it does, false otherwise.
	 */
	private static boolean gaurdUsername(spark.Request request, Response response) {
		if (request.session().attribute("username") == null) {
			response.status(401);
			response.body("Unauthorized");
			return false;
		}

		return true;
	}
}
