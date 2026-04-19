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
	@Test(priority =1)
	public void createBug()
	{
		RestAssured.baseURI = "https://praveenkumarsundarraj.atlassian.net";
		String response = given()
			.accept("application/json")
			.header("Authorization","Basic cHJhdmVlbmt1bWFyc3VuZGFycmFqQGdtYWlsLmNvbTpBVEFUVDN4RmZHRjAzcElSMWhXZmdLZ19FSUtEZzVLRDZYazNta0s1Y3NmMV91QVkzRThTMVJ4d3R1T2I3VXQyRklIQndMMmhjd1V3WV8wSmxfbm9QcDBoZ1BQdDBBakV1NnR5Uy1HaEZlcEd2bFVtTUFSZGZ3V1JRekt1a2owaFZ4WVBlN1dJcVVCYklEQlhfYlNqRWszVjNVMDV1Qzh5UElfT3dWNlZmaVVndGU4aGJiSm1pNEU9NjczM0U5Mzc=")
			.contentType("application/json")
			.body(JiraPayload.createBugPayload())
		.when()
			.post("rest/api/3/issue")
		.then()
			.assertThat().statusCode(201)
			.extract().response().asString();
		JiraAPITest.bugKey = ParseJson.getJsonValue(response, "key");
	}
	
	@Test(priority = 2)
	public void addAttachment()
	{
		RestAssured.baseURI = "https://praveenkumarsundarraj.atlassian.net";
		String response = given()
				.pathParam("bugKey", bugKey)
				.header("X-Atlassian-Token","no-check")
				.header("Authorization","Basic cHJhdmVlbmt1bWFyc3VuZGFycmFqQGdtYWlsLmNvbTpBVEFUVDN4RmZHRjAzcElSMWhXZmdLZ19FSUtEZzVLRDZYazNta0s1Y3NmMV91QVkzRThTMVJ4d3R1T2I3VXQyRklIQndMMmhjd1V3WV8wSmxfbm9QcDBoZ1BQdDBBakV1NnR5Uy1HaEZlcEd2bFVtTUFSZGZ3V1JRekt1a2owaFZ4WVBlN1dJcVVCYklEQlhfYlNqRWszVjNVMDV1Qzh5UElfT3dWNlZmaVVndGU4aGJiSm1pNEU9NjczM0U5Mzc=")
				.multiPart("file", new File("C:/Users/91900/Pictures/download.jpg"))
			.when()
				.post("/rest/api/3/issue/{bugKey}/attachments")
			.then()
				.assertThat().statusCode(200)
				.log().all()
				.extract().response().asString();
	}
}
