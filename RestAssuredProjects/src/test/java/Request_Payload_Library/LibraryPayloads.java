package Request_Payload_Library;

public class LibraryPayloads {

	public static String addBookPayload(String isbn, String aisleNumber)
	{
		return "{\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "\"name\":\"Learn Appium Automation with Java\",\r\n"
				+ "\"isbn\":\""+isbn+"\",\r\n"
				+ "\"aisle\":\""+aisleNumber+"\",\r\n"
				+ "\"author\":\"John foe\"\r\n"
				+ "}\r\n"
				+ "";
	}
	
	public static String deleteBookPayload(String ID)
	{
		return "{\r\n"
				+ "\"ID\" : \""+ID+"\"\r\n"
				+ "}";
	}
}
