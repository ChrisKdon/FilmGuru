package com.monikle.webserver.transformers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.ResponseTransformer;

/**
 * Serialized Java objects into their JSON representation.
 *
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public class JsonTransformer implements ResponseTransformer {
	private Gson gson = new Gson();

	@Override
	public String render(Object model) {
		return gson.toJson(model);
	}
}
