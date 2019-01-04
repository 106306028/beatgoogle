import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.security.cert.CertificateException;
import java.util.ArrayList;

import javax.xml.bind.ValidationException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WordCounter {
	private String urlStr;
	private String content;
	private String contentURL;
	public ArrayList<String> urlList = new ArrayList<>();
	public ArrayList<String> nameList = new ArrayList<>();
	
	public WordCounter(String urlStr) {
		this.urlStr = urlStr;
	}
	
	private String fetchContent() throws IOException {
		URL url = new URL(this.urlStr);
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		conn.connect();
		InputStream in = conn.getInputStream();
		InputStreamReader inReader = new InputStreamReader(in, "utf-8");
		BufferedReader br = new BufferedReader(inReader);
		
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
		
		
		return count;
	}
	
	public void findSubLink() throws IOException{
		if(this.contentURL == null) {
			this.contentURL = fetchContent();
		}
		Document document = Jsoup.parse(contentURL);
		String title = document.title();
		Elements lis = document.select("a[href]");
		int count = 0;
		for(Element li : lis) {
			try {
				
				Element cite = li.select("a").get(0);
				String citeUrl = cite.attr("href");
				citeUrl = URLDecoder.decode(citeUrl.substring(citeUrl.indexOf('=') + 1, citeUrl.indexOf('&')), "UTF-8");
				
				if (!citeUrl.startsWith("http")) {
			        continue; // Ads/news/etc.
				}
				urlList.add(citeUrl);
				nameList.add(title);
				count++;
				if(count > 2) {
					break;
				}
			}catch(Exception e) {
				
			}
		}
		 
			
		
		
	}
	
	public ArrayList<String> getURLList(){
		return urlList;
	}
	
	public ArrayList<String> getNameList(){
		return nameList;
	}

	
	
	
	
	
	
	
}
