package com.toolq.utils;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

/**
 * @author luoluo
 * @date 2020/10/2 15:07
 */
public class JsonUtil {
	private static final JsonParser parser = new JsonParser();

	public static boolean isJsonObject(String JSON) {
		return parser.parse(JSON) instanceof JsonObject;
	}

	public static JsonObject parseJsonObject(String JSON) {
		return (JsonObject) parser.parse(JSON);
	}

	public static JsonArray parseJsonArray(String JSON) {
		return (JsonArray) parser.parse(JSON);
	}

	public static String formatJson(String json) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(json);
		return gson.toJson(je);
	}
}
