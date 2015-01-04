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

	function loadMovies(onComplete) {
		$.getJSON("movies/random", onComplete);
	}

	$(function () {

		loadMovies(function(movies) {
			$("#movieList").html(templates.movieList({movies:movies}));
			initRatingControls();
		})
	});
})();