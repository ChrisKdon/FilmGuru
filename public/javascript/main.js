(function () {
	var $btnPageNext = $("#next-btn");
	var $btnPageBack = $("#back-btn");

	var session = {
		page: 1
	};

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

		$(".rating").change(onStarRatingChange);
	}

	/**
	 * Load popular movies from the server
	 */
	function loadPopularMovies(page, onLoadComplete) {
		$.getJSON("movies/popular/" + page, function (movies) {
			$("#movie-list-holder").html(templates.movieList({movies: movies}));
			initRatingControls();

			if(onLoadComplete) { onLoadComplete(); }
		});
	}

	/**
	 * Show/Hide page controls based on page number
	 */
	function renderPageControls() {
		if (session.page === 1) {
			$btnPageBack.hide();
			$btnPageNext.show();
		} else if (session.page === 1000) {
			$btnPageBack.show();
			$btnPageNext.hide();
		} else {
			$btnPageBack.show();
			$btnPageNext.show();
		}
	}

	function onBackPressed() {
		if (session.page > 1) {
			session.page -= 1;
			loadPopularMovies(session.page);
		}

		renderPageControls();
	}

	function onNextPressed() {
		if (session.page < 1000) {
			session.page += 1;
			loadPopularMovies(session.page);
		}

		renderPageControls();
	}

	function saveRating(movieId, rating) {
		$.get("/users/rate/movie/" + movieId + "?rating=" + rating);
	}

	/**
	 * Called when a star rating for a movie is changed
	 */
	function onStarRatingChange() {
		var rating = parseInt($(this).val(), 10);
		var movieId = $(this).data("movieid");

		saveRating(movieId, rating);

		$("#movie-" + movieId + " .star-rating").addClass("user");
	}

	$(function () {
		loadPopularMovies(session.page, function() {
			$btnPageBack.click(onBackPressed);
			$btnPageNext.click(onNextPressed);
		});
	});
})();