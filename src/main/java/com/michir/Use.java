package com.michir;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class Use implements Executor {

	private static final String patternString = "use (\\w+);*";
	private static Pattern pattern;

	String context = "";

	@PostConstruct
	void init() {
		pattern = Pattern.compile(patternString);
	}

	@Override
	public void run(String sql) {
		Matcher matcher = pattern.matcher(sql);
		if (matcher.find()) {
			context = matcher.group(1);
		}
		System.out.print(context.isEmpty() ? "sql> " : String.format("sql [%s]> ", context));
	}

	@Override
	public Boolean supported(String command) {
		return command.toLowerCase().matches(patternString);
	}
}
