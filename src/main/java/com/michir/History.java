package com.michir;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class History implements Executor {

	private static final String patternString = "h:(\\d+)";

	private static Pattern pattern;
	
	private LinkedList<String> history = new LinkedList<>();
	
	@Value("${history.maxSize:10}")
	private Integer maxSize;
	
	@Autowired
	QueryRunner queryRunner;
	
	@PostConstruct
	void init() {
		pattern = Pattern.compile(patternString);
	}
	
	@Override
	public void run(String cmd) throws Exception{
		if (cmd.matches(patternString)) {
			Matcher matcher = pattern.matcher(cmd);
			if (matcher.find()) {
				String sql = history.get(Integer.parseInt(matcher.group(1)));
				queryRunner.execute(sql);
			}
			
		} else {
			System.out.println("(hint) type 'h:index' to (re)run corresponding query");
			history.stream().forEach(e -> {
				System.out.println("["+history.indexOf(e)+"] "+e);
			});
		}
	}

	@Override
	public Boolean supported(String command) {
		return command.toLowerCase().equals("history") || command.equals("h") || command.toLowerCase().matches(patternString);
	}

	@EventListener
	public void handleEvent(QueryEvent event) {
		if (history.size() > maxSize) {
			history.poll();
		}
		if (history.contains(event.getQuery())) {
			history.poll();
		}
		history.add(event.getQuery());
	}
}
