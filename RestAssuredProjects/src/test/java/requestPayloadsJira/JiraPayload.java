package requestPayloadsJira;

public class JiraPayload {

	public static String createBugPayload()
	{
		return "{\r\n"
				+ "  \"fields\": {\r\n"
				+ "    \"project\": {\r\n"
				+ "      \"key\": \"SCRUM\"\r\n"
				+ "    },\r\n"
				+ "    \"summary\": \"My first automation bug\",\r\n"
				+ "    \"issuetype\": {\r\n"
				+ "      \"name\": \"Bug\"\r\n"
				+ "    },\r\n"
				+ "    \"priority\": {\r\n"
				+ "      \"name\": \"Medium\"\r\n"
				+ "    }\r\n"
				+ "  }\r\n"
				+ "}\r\n"
				+ "";
	}
}
