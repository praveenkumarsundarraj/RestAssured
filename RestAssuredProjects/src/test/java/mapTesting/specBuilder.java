package mapTesting;

import org.testng.Assert;
import org.testng.annotations.Test;

import Request_Payloads_Maps.MapPayload;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import reusableMethods.ParseJson;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class specBuilder {

	static String placeId;
	static String newAddress;
	static RequestSpecification res;
	
	
	@Test(priority = 1)
	public void addPlace() throws IOException
	{
		//common spec builder to reuse the redundant parameters
		res = new RequestSpecBuilder()
					.setBaseUri("https://rahulshettyacademy.com")
					.addQueryParam("key", "qaclick123")
					.setContentType("application/json")
					.build();
		
		
		String dir = System.getProperty("user.dir");
		Path jsonPath = Paths.get(dir,"/src/test/java/Request_Payloads_Maps/addPlace.json");
		String response = given().spec(res)
			.body(new String(Files.readAllBytes(jsonPath)))
			.log().all()
		.when()
			.post("maps/api/place/add/json")
		.then()
			.statusCode(200).body("status", equalTo("OK"))
			.extract().response().asString();
		specBuilder.placeId = ParseJson.getJsonValue(response,"place_id");
		System.out.println(specBuilder.placeId);
	}
	
	@Test(priority = 2)
	public void updatePlace()
	{
		specBuilder.newAddress = "192b, mahalakshmi nager, India";
		
		given()
			.spec(res)
			.header("Content-Type","application/json")
			.body(MapPayload.updateMapPayload(specBuilder.placeId,specBuilder.newAddress))
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
			.spec(res)
			.queryParam("place_id", specBuilder.placeId)
		.when()
			.get("maps/api/place/get/json")
		.then()
			.statusCode(200)
			.extract().response().asString();
		Assert.assertEquals(specBuilder.newAddress, ParseJson.getJsonValue(response, "address"));
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
