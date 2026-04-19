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
	
	public static String authKeyBasic()
	{
		return "Basic cHJhdmVlbmt1bWFyc3VuZGFycmFqQGdtYWlsLmNvbTpBVEFUVDN4RmZHRjAzcElSMWhXZmdLZ19FSUtEZzVLRDZYazNta0s1Y3NmMV91QVkzRThTMVJ4d3R1T2I3VXQyRklIQndMMmhjd1V3WV8wSmxfbm9QcDBoZ1BQdDBBakV1NnR5Uy1HaEZlcEd2bFVtTUFSZGZ3V1JRekt1a2owaFZ4WVBlN1dJcVVCYklEQlhfYlNqRWszVjNVMDV1Qzh5UElfT3dWNlZmaVVndGU4aGJiSm1pNEU9NjczM0U5Mzc=";
	}
}
