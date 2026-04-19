package Library_API;

import io.restassured.RestAssured;
import reusableMethods.ParseJson;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import Request_Payload_Library.AddBookPayload;
import Request_Payload_Library.LibraryPayloads;

public class LibraryTesting {
	
	@Test(dataProvider = "bookData")
	public void addBook(String isbn, String aisleNumber)
	{
		AddBookPayload abp = new AddBookPayload();
		abp.setName("Learn Appium Automation with Java");
		abp.setIsbn(isbn);
		abp.setAisle(aisleNumber);
		abp.setAuthor("John foe");
		
		
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given()
			.header("Content-Type", "application/json")
			.body(abp)
		.when()
			.post("Library/Addbook.php")
		.then()
			.assertThat().statusCode(200)
			.body("Msg", equalTo("successfully added"))
			.log().all()
			.extract().response().asString();
		String bookID = ParseJson.getJsonValue(response, "ID");
	}

//	@Test(dataProvider = "bookData")
//	public void addBook(String isbn, String aisleNumber)
//	{
//		RestAssured.baseURI = "http://216.10.245.166";
//		String response = given()
//			.body(LibraryPayloads.addBookPayload(isbn,aisleNumber))
//		.when()
//			.post("Library/Addbook.php")
//		.then()
//			.assertThat().statusCode(200)
//			.body("Msg", equalTo("successfully added"))
//			.extract().response().asString();
//		String bookID = ParseJson.getJsonValue(response, "ID");
//	}
	
	@Test(dataProvider = "bookData")
	public void deleteBook(String isbn, String aisleNumber)
	{
		RestAssured.baseURI = "http://216.10.245.166";
		given()
			.body(LibraryPayloads.deleteBookPayload(isbn+aisleNumber))
		.when()
			.delete("/Library/DeleteBook.php")
		.then()
			.assertThat().statusCode(200)
			.body("msg", equalTo("book is successfully deleted"));
	}
	
	@DataProvider(name = "bookData")
	public Object[][] getBookData()
	{
		return new Object[][] {{"isbn","1065"}, {"isbn","1066"}, {"isbn","1067"}, {"isbn","1068"}};
	}
}
