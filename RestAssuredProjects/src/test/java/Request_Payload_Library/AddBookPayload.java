package Request_Payload_Library;

public class AddBookPayload {

	private String name;
	private String isbn;
	private String aisle;
	private String author;
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setIsbn(String isbn)
	{
		this.isbn = isbn;
	}
	
	public String getIsbn()
	{
		return this.isbn;
	}
	
	public void setAisle(String aisle)
	{
		this.aisle = aisle;
	}
	
	public String getAisle()
	{
		return this.aisle;
	}
	
	public void setAuthor(String author)
	{
		this.author = author;
	}
	
	public String getAuthor()
	{
		return this.author;
	}
}
