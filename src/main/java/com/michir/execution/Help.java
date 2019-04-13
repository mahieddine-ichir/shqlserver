package com.michir.execution;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.michir.execution.Executor;
import org.springframework.stereotype.Component;

@Component
public class Help implements Executor {

	Map<String, String> map = new HashMap<>();
	
	@PostConstruct
	void init() {
		map.put("use <schema>; exec sp_columns <table>;", "Describe table <table> on schema <schema>");
		map.put("describe table <schema>..<table>", "alias of <exec sp_columns>");
	}
	
	@Override
	public void run(String sql) {
		System.out.println("================");
		map.forEach((k,v) -> System.out.println(String.format("\t%s \t%s", k, v)));
		System.out.println("================");
	}

	@Override
	public Boolean supported(String command) {
		return command.equalsIgnoreCase("help") || command.equals("?");
	}
}
