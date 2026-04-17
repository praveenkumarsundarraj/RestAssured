package reusableMethods;

import io.restassured.path.json.JsonPath;

public class ParseJson {

	public static String getJsonValue(String response, String searchString)
	{
		JsonPath js = new JsonPath(response);
		return js.getString(searchString);
	}
}
