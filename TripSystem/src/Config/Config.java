package Config;

public class Config {
	private String database = "trip";
	private String url = "jdbc:mysql://127.0.0.1:3306/" + database + "?useUnicode=true&amp;characterEncoding=UTF-8&amp;";
	
//	private static String password = "mm941216";
	private static String password = "950720SophiaYj";
	
	public String getUrl(){
		return url;
	}
	
	public static String getPassword() {
		return password;
	}
}
