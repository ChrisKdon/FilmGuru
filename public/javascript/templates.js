/**
 * Compiled versions of all the templates.
 */

(function() {
	Handlebars.registerPartial("movie", $("#partial-movie").html());

	window.templates = {
		movieList: Handlebars.compile($("#template-movieList").html())
	};
})();