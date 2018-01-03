package com.michir;

import org.springframework.stereotype.Component;

@Component
public class SQLCommandAliasParser {

	boolean contains(final String alias) {
		return Alias.all().anyMatch(a -> a.matches(alias));
	}
	
	String parse(String input) {
		return Alias.all().filter(a -> a.matches(input)).findFirst().map(a -> a.parse(input)).get();
	}
}
