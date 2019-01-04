import java.util.ArrayList;

public class KeywordList {
	public ArrayList<Keyword> list;
	
	public KeywordList() {
		this.list = new ArrayList<>();
	}
	
	public void add(Keyword keyword) {
			list.add(keyword);
	}
	
	public void outputIndex(int n) {
		if(n >= list.size()) {
			System.out.println("Invalid Operation");
			return;
		}
		
		ArrayList<Keyword> results = new ArrayList<>();
		Keyword k = list.get(n);
		results.add(k);
		
		printKeywordList(results);
		
	}
	
	
	
	public void outputHas(String pattern) {
		ArrayList<Keyword> results = new ArrayList<>();
		for(int i = 0; i < list.size(); i++) {
			Keyword k = list.get(i);
			
			if(k.name.contains(pattern)) {
				results.add(k);
			}
		}
		
		if(results.isEmpty()) {
			System.out.println("Not Found");
		} else {
			printKeywordList(results);
		}
		
	}
	
	public void outputName(String name) {
		ArrayList<Keyword> results = new ArrayList<>();
		
		for(int i = 0; i < list.size(); i++) {
			Keyword k = list.get(i);
			
			if(k.name.equals(name)) {
				results.add(k);
			}
		}
		
		if(results.isEmpty()) {
			System.out.println("Not Found");
		} else {
			printKeywordList(results);
		}
		
	}
	
	public void outputFirstN(int n) {
		if ( n > list.size()) {
			System.out.println("Invalid Operation");
			return;
		}
		
		ArrayList<Keyword> results = new ArrayList<>();
		for(int i = 0; i < n; i++) {
			Keyword k = list.get(i);
			results.add(k);
		}
		
		printKeywordList(results);
		
	}
	
	
	public void deleteIndex(int n) {
		if( n > list.size()) {
			System.out.println("Invalid Operation");
			return;
		}
		
		list.remove(n);
		System.out.println("Done");
		
	}
	
	
	public void deleteHas(String pattern) {
		ArrayList<Keyword> found = new ArrayList<>();
		
		for(int i = 0; i < list.size(); i++) {
			Keyword k = list.get(i);
			
			if(k.name.contains(pattern)) {
				found.add(k);
			}
		}
		
		if(found.isEmpty()) {
			System.out.println("Not Found");
		} else {
			list.removeAll(found);
			System.out.println("Done");
		}
		
	}
	
	public void deleteName(String name) {
		ArrayList<Keyword> found = new ArrayList<>();
		
		for(int i = 0; i < list.size(); i++) {
			Keyword k = list.get(i);
			
			if(k.name.equals(name)) {
				found.add(k);
			}
		}
		
		if(found.isEmpty()) {
			System.out.println("Not Found");
		} else {
			list.removeAll(found);
			System.out.println("Done");
		}
		
	}
	
	public void deleteFirstN(int n) {
		if ( n > list.size()) {
			System.out.println("Invalid Operation");
			return;
		}
		
		for(int i = 0; i < n ; i++) {
			list.remove(0);
		}
		System.out.println("Done");
	}
	
	public void deleteAll() {
		list.clear();
		System.out.println("Done");
	}
	
	private void printKeywordList(ArrayList<Keyword> kList) {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < kList.size(); i++) {
			Keyword k = kList.get(i);
			
			if(i > 0) {
				sb.append(" ");
			}
			sb.append(k.toString());
		}
		
		System.out.println(sb.toString());
	}

	
	
	
	
}