package com.michir;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class SQLCommandAliasParser {

	Map<String, String> map = new HashMap<>();
	
	@PostConstruct
	void init() {
		map.put("\\s*describe\\s+table\\s+(\\w+)\\.{2}(\\w+)\\s*;*\\s*", "use %s; exec sp_columns %s;");
	}
	
	boolean contains(String alias) {
		return map.keySet().stream().filter(e -> alias.matches(e)).findAny().isPresent();
	}
	
	String parse(String input) {
		String key = map.keySet().stream().filter(e -> input.matches(e)).findFirst().get();
		Pattern pattern = Pattern.compile(key);
		Matcher matcher = pattern.matcher(input);
		if (matcher.find()) {
			Object[] s = new String[matcher.groupCount()];
			for (int i=1; i<=matcher.groupCount();i++) {
				s[i-1] = matcher.group(i);
			}
			return String.format(map.get(key), s);
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println("describe table MLV_SICL..request".matches("describe table (\\w+)\\.\\.(\\w+)"));
	}
	
}
