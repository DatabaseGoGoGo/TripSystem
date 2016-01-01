package Config;

public class Config {
	private String database = "trip";
	private String url = "jdbc:mysql://127.0.0.1:3306/" + database + "?useUnicode=true&amp;characterEncoding=UTF-8&amp;";

	public String getUrl(){
		return url;
	}
}
