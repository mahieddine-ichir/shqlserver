package com.michir;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class SQLCommandsHelper {

	Map<String, String> map = new HashMap<>();
	
	@PostConstruct
	void init() {
		map.put("use <schema>; exec sp_columns <table>;", "Describe table <table> on schema <schema>");
		map.put("describe table <schema>..<table>", "alias of <exec sp_columns>");
	}
	
	public void run() {
		
		System.out.println("================");
		map.forEach((k,v) -> System.out.println(String.format("\t%s \t%s", k, v)));
		System.out.println("================");
	}
}
