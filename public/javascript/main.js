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
	}

	/**
	 * Load popular movies from the server
	 */
	function loadPopularMovies(page) {
		$.getJSON("movies/popular/" + page, function(movies) {
			$("#movie-list-holder").html(templates.movieList({movies:movies}));
			initRatingControls();
		});
	}

	function renderPageControls() {
		if(session.page === 1) {
			$btnPageBack.hide();
			$btnPageNext.show();
		} else if(session.page === 1000) {
			$btnPageBack.show();
			$btnPageNext.hide();
		} else {
			$btnPageBack.show();
			$btnPageNext.show();
		}
	}

	function onBackPressed() {
		if(session.page > 1) {
			session.page -= 1;
			loadPopularMovies(session.page);
		}

		renderPageControls();
	}

	function onNextPressed() {
		if(session.page < 1000) {
			session.page += 1;
			loadPopularMovies(session.page);
		}

		renderPageControls();
	}

	$(function () {
		loadPopularMovies(session.page);

		$btnPageBack.click(onBackPressed);
		$btnPageNext.click(onNextPressed);
	});
})();