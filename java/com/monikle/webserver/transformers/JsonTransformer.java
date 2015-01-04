package com.monikle.webserver.transformers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.monikle.webserver.models.Movie;
import spark.ResponseTransformer;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public class JsonTransformer implements ResponseTransformer {
	private Gson gson;

	public JsonTransformer() {
		GsonBuilder gson = new GsonBuilder();

		this.gson = gson.create();
	}

	@Override
	public String render(Object model) {
		return gson.toJson(model);
	}
}
