package com.monikle.webserver.rater;

import com.monikle.memdb.MovieDatabase;
import com.monikle.neuro.FeedForwardNetwork;
import com.monikle.neuro.NeuralNetwork;
import com.monikle.neuro.TrainerConfiguration;
import com.monikle.neuro.TrainingData;
import com.monikle.neuro.math.Vector;
import com.monikle.webserver.Config;
import com.monikle.webserver.models.MovieDetail;
import com.monikle.webserver.models.MovieRating;

import java.util.List;
import java.util.Map;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class MovieRater {
	private static MovieDatabase db = MovieDatabase.getDb();

	private final String username;
	private final NeuralNetwork network;
	private final Map<String, Integer> nodeIndices;
	private final int inputCount, outputCount;
	private final int minYear, maxYear;

	public MovieRater(String username, int maxRating, int hiddenNodeCount,
										double learningRate, double momentum) {

		this.nodeIndices = Config.NODE_INDICES;
		this.inputCount = Config.NODE_INDICES.size();
		this.minYear = Config.MIN_YEAR;
		this.maxYear = Config.MAX_YEAR;
		this.outputCount = maxRating;

		this.username = username;

		this.network = new FeedForwardNetwork(inputCount, hiddenNodeCount, maxRating, learningRate, momentum);
	}

	/**
	 * Train the rater on any new input information.
	 *
	 * @throws Exception
	 */
	public void train() throws Exception {
		List<MovieRating> ratings = db.ratings.getMovieRatings(username);

		TrainingData data = new TrainingData();

		ratings.forEach(rating -> {
			data.add(mapMovieToNetworkInput(rating.getMovie()), mapRatingToNetworkOutput(rating.getRating().orElse(0)));
		});

		TrainerConfiguration config = TrainerConfiguration.create(data)
				.setAcceptableError(1)
				.setMaxEpochs(1000)
				.setShuffleTrainingData(true);

		network.train(config);
	}

	public int getRating(MovieDetail movie) {
		return getRatingFromResult(network.run(mapMovieToNetworkInput(movie)).getValues());
	}

	private double[] mapRatingToNetworkOutput(int rating) {
		double[] output = new double[outputCount];
		output[rating - 1] = 1;
		return output;
	}

	/**
	 * Convert a movie and all it's metadata into the double valued
	 * input form for the neural network.
	 *
	 * @param movie
	 * @return
	 */
	private double[] mapMovieToNetworkInput(MovieDetail movie) {
		double[] input = new double[inputCount];

		mapGenreNodes(movie.getGenres(), input);
		mapYearNode(movie.getYear(), input);
		mapEnglishNode(movie.isEnglish(), input);

		return input;
	}

	private void mapEnglishNode(boolean isEnglish, double[] writeTo) {
		writeTo[nodeIndices.get("English")] = isEnglish ? 1 : 0;
	}

	private void mapYearNode(int year, double[] writeTo) {
		if (year < minYear) {
			year = minYear;
		}
		if (year > maxYear) {
			year = maxYear;
		}

		double value = scale(minYear, maxYear, 0, 1, year);

		writeTo[nodeIndices.get("Year")] = value;
	}

	private void mapGenreNodes(String[] genres, double[] writeTo) {
		for (String genre : genres) {
			int index = nodeIndices.getOrDefault(genre, -1);
			if (index != -1) {
				writeTo[index] = 1;
			}
		}
	}

	private static double scale(double iMin, double iMax, double min, double max, double input) {
		if(max < min) {
			throw new IllegalArgumentException("Max must be less than min.");
		}

		if(input < iMin || input > iMax) {
			throw new IllegalArgumentException("Input must be in range [" + iMin + ", " + iMax + "]. Was: " + input);
		}

		return (((max - min)*(input - iMin))/(iMax - iMin)) + min;
	}

	private static int getRatingFromResult(double[] networkResult) {
		double max = 0;
		int rating = 0;

		for (int i = 0; i < networkResult.length; i++) {
			if (networkResult[i] >= max) {
				rating = i + 1;
				max = networkResult[i];
			}
		}

		return rating;
	}
}
