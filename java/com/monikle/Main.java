package com.monikle;

import com.monikle.webserver.transformers.JsonTransformer;

import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.get;

public class Main {
	public static void main(String[] args) {
		externalStaticFileLocation("./public"); // Static files

		get("/test", "application/json", (req, res) -> new Test(), new JsonTransformer());

		get("/hello", (req, res) -> "Hello World2");
	}

	private static class Test {
		public int x = 10;
	}
}
