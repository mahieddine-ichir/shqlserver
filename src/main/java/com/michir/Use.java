package com.michir;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class Use implements Executor {

	private static final String patternString = "use (\\w+);*";
	private static Pattern pattern;

	String context = "";

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostConstruct
	void init() {
		pattern = Pattern.compile(patternString);
	}

	public void onStart() {
		System.out.print("sql [master]> ");
	}
	
	@Override
	public void run(String cmd) {
		Matcher matcher = pattern.matcher(cmd);
		if (matcher.find()) {
			context = matcher.group(1);
			
			System.out.print(context.isEmpty() ? "sql [master]> " : String.format("sql [%s]> ", context));
			publisher.publishEvent(new SqlContextChangedEvent(context));
		}
	}

	@Override
	public Boolean supported(String command) {
		return command.toLowerCase().matches(patternString);
	}
}
