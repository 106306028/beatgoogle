import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleQuery {
	public String searchKeyword;
	public String url;
	public String content;
	public ArrayList<String> urlList = new ArrayList<>();
	public ArrayList<String> nameList = new ArrayList<>();
	private int httpOK = HttpURLConnection.HTTP_OK;
	private final int error504 = HttpURLConnection.HTTP_GATEWAY_TIMEOUT;
	private final int error403 = HttpURLConnection.HTTP_FORBIDDEN;
	private final int error500 = HttpURLConnection.HTTP_INTERNAL_ERROR;
	private final int error404 = HttpURLConnection.HTTP_NOT_FOUND;
	
	public GoogleQuery(String searchKeyword) {
		this.searchKeyword = searchKeyword;
		this.url = "https://www.google.com.tw/search?q=" + searchKeyword + "&oe=utf8&num=100"; 
	}
	private String fetchContent() throws IOException {
		String retVal = "";
		URL urlStr = new URL(this.url);
		URLConnection connection = urlStr.openConnection();
		connection.setRequestProperty("User-agent", 
	              "Mozilla/5.0 (Windows; U; Windows NT 6.0; zh-TW; rv:1.9.1.2) " 
	            		  + "Gecko/20090729 Firefox/3.5.2 GTB5 (.NET CLR 3.5.30729)");
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
	
	public HashMap<String, String> query() throws IOException {
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
				
				Element cite = li.select("a").get(0);
				String citeUrl = cite.attr("href");
				citeUrl = URLDecoder.decode(citeUrl.substring(citeUrl.indexOf('=') + 1, citeUrl.indexOf('&')), "UTF-8");
				
				if (!citeUrl.startsWith("http")) {
			        continue; // Ads/news/etc.
				}
				
				if (citeUrl.indexOf("taiwan") != -1) {
			        continue; // Ads/news/etc.
				}
				
				URL url = new URL(citeUrl);
				HttpURLConnection uc = (HttpURLConnection) url.openConnection();
				uc.setReadTimeout(30000);
				uc.connect();
				int status = uc.getResponseCode();
				if(status != httpOK) {
					switch(status){
						case error504:
							System.out.println("連線網址逾時!");
							break;
						case error403:
							System.out.println("連線網址禁止!");
							break;
						case error500:
							System.out.println("連線網址錯誤或不存在!");
							break;
						case error404:
							System.out.println("連線網址不存在!");
							break;
						default:
							System.out.println("連線網址發生未知錯誤!");	
					}
					continue;
				}
				urlList.add(citeUrl);
				nameList.add(title);
				
				System.out.println(title + " " + citeUrl);
				retVal.put(title, citeUrl);
			}catch(Exception e){
				
			}
		}
		return retVal;
	}

}
