import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleQuery {
	public String searchKeyword;
	public String url;
	public String content;
	
	public GoogleQuery(String searchKeyword) {
		this.searchKeyword = searchKeyword;
		this.url = "https://www.google.com.tw/search?q=" + searchKeyword + "&oe=utf8&num=100"; 
	}
	private String fetchContent() throws IOException {
		String retVal = "";
		URL urlStr = new URL(this.url);
		URLConnection connection = urlStr.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; zh-TW; rv:1.9.1.2) "+ "Gecko/20090729 Firefox/3.5.2 GTB5 (.NET CLR 3.5.30729)");
		connection.connect();
		InputStream inputStream = connection.getInputStream();
		InputStreamReader inReader = new InputStreamReader(inputStream, "utf-8");
		BufferedReader bf = new BufferedReader(inReader);
		
		String line = null;
		while((line = bf.readLine()) != null) {
			retVal += line;
		}
		return retVal;
	}
	
	public HashMap<String, String> query() throws IOException{
		if(this.content == null) {
			this.content = fetchContent();
		}
		HashMap<String, String> retVal = new HashMap<String, String>();
		Document document = Jsoup.parse(this.content);
		Elements lis = document.select("div.g");
		
		for(Element li : lis) {
			try {
				Element h3 = li.select("h3.r").get(0);
				String title = h3.text();
				
				Element cite = li.select("cite").get(0);
				String citeUrl = cite.text();
				System.out.println(title + " " + citeUrl);
				WebPage rootPage = new WebPage(citeUrl, title);
				WebTree tree = new WebTree(rootPage);
				retVal.put(title, citeUrl);
			}catch(IndexOutOfBoundsException e){
				//Do nothing
			}
		}
		return retVal;
	}

}
