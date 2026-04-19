package authenticationTesting;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import pojoAuthentication.API;
import pojoAuthentication.GetCourse;
import pojoAuthentication.Mobile;
import pojoAuthentication.WebAutomation;

import org.testng.annotations.Test;


public class OAuthValidation {

	static String token;
	@Test(priority = 1)
	public void getToken()
	{
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given()
				.formParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.formParams("grant_type", "client_credentials")
				.formParams("scope", "trust")
			.when()
				.post("oauthapi/oauth2/resourceOwner/token")
			.then()
				.assertThat().statusCode(200)
				.body("token_type", equalTo("Bearer"))
				.extract().response().asString();
		JsonPath js = new JsonPath(response);
		token = js.getString("access_token");
		System.out.println("Token: "+token);
	}
	
	@Test(priority = 2)
	public void getCourseDetails()
	{		
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		GetCourse gc = given()
			.param("access_token", token)
		.when()
			.get("oauthapi/getCourseDetails")
		.then()
			.assertThat().statusCode(401)
			.extract().as(GetCourse.class);
//		System.out.println(gc.getLinkedIn());
		
		//Get API class
		int totalPrice=0;
		List<API> getApi = gc.getCourses().getApi();
		for(int i=0;i<getApi.size();i++)
		{
			API values = getApi.get(i);
			String courseTitle= values.getCourseTitle();
			if(courseTitle.equalsIgnoreCase("SoapUI Webservices testing"))
			{
				System.out.println(courseTitle);
				System.out.println(values.getPrice());
			}
			totalPrice+=Integer.parseInt(values.getPrice());
		}
		
		List<WebAutomation> webAuto = gc.getCourses().getWebAutomation();
		System.out.println("WebAutomation CourseTitles:");
		for(int i=0;i<webAuto.size();i++)
		{
			System.out.println(webAuto.get(i).getCourseTitle());
			totalPrice+=Integer.parseInt(webAuto.get(i).getPrice());
		}
		System.out.println();

		List<Mobile> Mobile = gc.getCourses().getMobile();
		for(int i=0;i<Mobile.size();i++)
		{
			totalPrice+=Integer.parseInt(Mobile.get(i).getPrice());
		}
		
		System.out.println("Total Price of all Three Courses: "+totalPrice);
		
	}
}
