package com.michir;

import java.util.stream.Stream;

import org.springframework.stereotype.Component;

@Component
public class SQLCommandAliasParser {

	Stream<Alias> aliases = Alias.all();
	
	boolean contains(final String alias) {
		return aliases.anyMatch(a -> a.matches(alias));
	}
	
	String parse(String input) {
		return aliases.filter(a -> a.matches(input)).findFirst().map(a -> a.parse(input)).get();
	}
}
