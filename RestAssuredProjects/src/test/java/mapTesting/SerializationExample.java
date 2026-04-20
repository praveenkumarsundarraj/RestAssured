package mapTesting;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;
import io.restassured.RestAssured;
import pojoMap.Location;
import pojoMap.AddPlace;
import pojoMap.AddPlaceResponse;


public class SerializationExample {

	@Test
	public void addPlace()
	{
		AddPlace addPlace = new AddPlace();
		Location l1 =new Location();
		l1.setLat(-38.383494);
		l1.setLng(33.427362);
		addPlace.setLocation(l1);
		addPlace.setAccuracy(50);
		addPlace.setName("Praveens villa");
		addPlace.setPhone_number("(+91) 983 893 3937");
		addPlace.setAddress("29, side layout, cohen 09");
		List<String> types = new ArrayList<String>();
		types.add("shoe park");
		types.add("shop");
		addPlace.setTypes(types);
		addPlace.setWebsite("http://google.com");
		addPlace.setLanguage("Tamil");
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		AddPlaceResponse response =given()
			.queryParam("key","qaclick123")
			.contentType("application/json")
			.body(addPlace)
		.when()
			.post("maps/api/place/add/json")
		.then()
			.assertThat().statusCode(200)
			.body("status", equalTo("OK"))
			.extract().as(AddPlaceResponse.class);
		System.out.println(
				response.getId()
				+"\n"+response.getPlace_id()
				+"\n"+response.getReference()
				+"\n"+response.getScope()
				+"\n"+response.getStatus()
				);
			
	}
}
