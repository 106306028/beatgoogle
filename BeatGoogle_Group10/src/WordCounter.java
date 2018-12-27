import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class WordCounter {
	private String urlStr;
	private String content;
	public ArrayList<String> urlList;
	
	public WordCounter(String urlStr) {
		this.urlStr = urlStr;
	}
	
	private String fetchContent() throws IOException {
		URL url = new URL(this.urlStr);
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		String retVal = "";
		String line = null;
		
		while((line = br.readLine()) != null) {
			retVal  = retVal + line + "\n";
		}
		
		return retVal;
	}
	
	public int countKeyword(String keyword) throws IOException {
		if(content == null) {
			content = fetchContent();
		}
		content = content.toUpperCase();
		String contentURL = content.toLowerCase();
		keyword = keyword.toUpperCase();
		int index = 0;
		int count = 0;
		
		while(index != -1){
			index = content.indexOf(keyword);
			if(index != -1) {
				count++;
				content = content.substring(index + keyword.length());
			}
			
		}
		
		while(index != -1){
			//<a href="連結網址">連結名稱</a>
			index = content.indexOf("<a href=http:");
			if(index != -1) {
				count++;
				content = content.substring(index + keyword.length());
			}
			
		}
		
		return count;
	}

	
	
	
	
	
	
	
}
