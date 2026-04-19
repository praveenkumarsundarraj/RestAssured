package mapTesting;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import pojoMap.AddPlace;
import pojoMap.Location;
import reusableMethods.ParseJson;

public class SerializeExample {

	@Test(priority = 1)
	public void addPlace() throws IOException
	{
		AddPlace add = new AddPlace();
		add.setAccuracy(50);
		add.setAddress("29, paraniputhur, India");
		add.setName("Frontline house");
		add.setPhone_number("(+91) 983 893 3937");
		add.setWebsite("http://google.com");
		add.setLanguage("Tamil-IN");
		Location loc = new Location();
		loc.setLat(-38.383494);
		loc.setLng(33.427362);		
		add.setLocation(loc);
		List<String> type=new ArrayList<String>();
		type.add("shoe park");
		type.add("shop");
		add.setTypes(type);
		
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		String response = given()
			.queryParam("key","qaclick123")
			.body(add)
		.when()
			.post("maps/api/place/add/json")
		.then()
			.statusCode(200).body("status", equalTo("OK"))
			.extract().response().asString();
		MapAPI.placeId = ParseJson.getJsonValue(response,"place_id");
		System.out.println(MapAPI.placeId);
		System.out.println(response);
	}
}
