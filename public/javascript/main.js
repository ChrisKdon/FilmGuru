(function () {
	/**
	 * Render the star rating controls
	 */
	function initRatingControls() {
		$(".rating").rating({
			min: 1,
			max: 5,
			step: 0.5,
			showClear: false,
			showCaption: false,
			size: "xs"
		});
	}

	/**
	 * Load random movies from the server.
	 *
	 * @param onComplete
	 */
	function loadRandomMovies(onComplete) {
		$.getJSON("movies/random", onComplete);
	}

	$(function () {
		loadRandomMovies(function(movies) {
			$("#movieList").html(templates.movieList({movies:movies}));
			initRatingControls();
		});
	});
})();