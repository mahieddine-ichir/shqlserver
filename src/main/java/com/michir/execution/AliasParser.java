package com.michir.execution;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import com.michir.QueryRunner;
import com.michir.execution.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AliasParser implements Executor {

	@Autowired
	QueryRunner commandRunner;
	
	Map<String, String> map = new HashMap<>();
	
	@PostConstruct
	void init() {
		map.put("describe\\s+table\\s+(\\w+)\\.{2}(\\w+)\\s*;*", "use %s; exec sp_columns %s;");
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
	
	@Override
	public Boolean supported(String command) {
		return contains(command);
	}

	@Override
	public void run(String sql) throws Exception {
		this.commandRunner.execute(parse(sql));
	}
	
}
