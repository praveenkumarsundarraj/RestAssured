package mapTesting;

import org.testng.Assert;
import org.testng.annotations.Test;

import Request_Payloads_Maps.MapPayload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import reusableMethods.ParseJson;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class MapAPI {

	static String placeId;
	static String newAddress;
	@Test(priority = 1)
	public void addPlace() throws IOException
	{
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		Path jsonPath = Paths.get("H:\\Rest Assured\\RestAssured\\RestAssuredProjects\\src\\test\\java\\Request_Payloads_Maps\\addPlace.json");
		String response = given()
			.queryParam("key","qaclick123")
			.body(new String(Files.readAllBytes(jsonPath)))
		.when()
			.post("maps/api/place/add/json")
		.then()
			.statusCode(200).body("status", equalTo("OK"))
			.extract().response().asString();
		MapAPI.placeId = ParseJson.getJsonValue(response,"place_id");
		System.out.println(MapAPI.placeId);
	}
	
	@Test(priority = 2)
	public void updatePlace()
	{
		MapAPI.newAddress = "192b, mahalakshmi nager, India";
		given()
			.queryParam("key", "qaclick123")
			.header("Content-Type","application/json")
			.body(MapPayload.updateMapPayload(MapAPI.placeId,MapAPI.newAddress))
		.when()
			.put("maps/api/place/update/json")
		.then()
			.assertThat().statusCode(200)
			.body("msg",equalTo("Address successfully updated"));
	}
	
	@Test(priority = 3)
	public void getPlace()
	{
		String response = given()
			.queryParam("key", "qaclick123")
			.queryParam("place_id", MapAPI.placeId)
		.when()
			.get("maps/api/place/get/json")
		.then()
			.statusCode(200)
			.extract().response().asString();
		Assert.assertEquals(MapAPI.newAddress, ParseJson.getJsonValue(response, "address"));
	}
	
	@Test
	public void complexJsonParse()
	{
		JsonPath validate = new JsonPath(MapPayload.getComplexJson());
		int courseSize = validate.getInt("courses.size()");
		System.out.println(courseSize);
		int purchaseAmount = validate.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);
		System.out.println(validate.getString("courses[0].title"));
		int totalPrice = 0;
		for(int i=0;i<courseSize;i++)
		{
			String title= validate.getString("courses["+i+"].title");
			int price = validate.getInt("courses["+i+"].price");
			int copies = validate.getInt("courses["+i+"].copies");
			
			System.out.println("Title: "+title+"\nPrice: "+price);
			totalPrice+= (price * copies);
			
			if(title.equals("RPA"))System.out.println("No of Copies for RPA: "+copies);
		}
		Assert.assertEquals(purchaseAmount, totalPrice);
	}
}
