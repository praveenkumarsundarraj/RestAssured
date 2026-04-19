package jiraAPITest;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import requestPayloadsJira.JiraPayload;
import reusableMethods.ParseJson;

public class JiraAPITest {

	static String bugKey;
	static String bugId;
	@Test(priority =1)
	public void createBug()
	{
		RestAssured.baseURI = "https://praveenkumarsundarraj.atlassian.net";
		String response = given()
			.accept("application/json")
			.header("Authorization",JiraPayload.authKeyBasic())
			.contentType("application/json")
			.body(JiraPayload.createBugPayload())
		.when()
			.post("rest/api/3/issue")
		.then()
			.assertThat().statusCode(201)
			.extract().response().asString();
		JiraAPITest.bugKey = ParseJson.getJsonValue(response, "key");
		JiraAPITest.bugId = ParseJson.getJsonValue(response, "id");
	}
	
	@Test(priority = 2)
	public void addAttachment()
	{
		RestAssured.baseURI = "https://praveenkumarsundarraj.atlassian.net";
		String response = given()
				.pathParam("bugKey", bugKey)
				.header("X-Atlassian-Token","no-check")
				.header("Authorization",JiraPayload.authKeyBasic())
				.multiPart("file", new File("C:/Users/91900/Pictures/download.jpg"))
			.when()
				.post("/rest/api/3/issue/{bugKey}/attachments")
			.then()
				.assertThat().statusCode(200)
				.extract().response().asString();
	}
	
	@Test(priority = 3)
	public void getIssue()
	{
		System.out.println("Bug Id: "+bugId);
		RestAssured.baseURI = "https://praveenkumarsundarraj.atlassian.net";
		given()
			.pathParam("bugId", bugId)
			.accept("application/json")
			.header("Authorization",JiraPayload.authKeyBasic())
			.when()
			.get("/rest/api/3/issue/{bugId}")
		.then()
			.assertThat().statusCode(200)
			.body("fields.attachment[0].filename", equalTo("download.jpg"));
	}
}
