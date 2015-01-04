package com.monikle.webserver.transformers;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 * Date:      2015-01-04.
 */
public class JsonTransformer implements ResponseTransformer {
	private Gson gson = new Gson();

	@Override
	public String render(Object model) {
		return gson.toJson(model);
	}
}
