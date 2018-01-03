package com.michir;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Definition of an alias command.
 * Example: describe <pre>schema_name.table_name</pre> is an alias of <pre>use schema_name; exec sp_columns table_name</pre>.
 * 
 * @author michir
 */
public class Alias {

	/**
	 * The simplified alias of the {@link #working}.
	 */
	private final String alias;
	
	/**
	 * A predicate to determine whether an input matches <code>this</code> alias.
	 */
	private final Predicate<String> e;

	/**
	 * An extractor function to get execution context from input alias command.
	 */
	private final Function<String, String> map;
	
	private Alias(String alias, String description, Predicate<String> e, Function<String, String> map) {
		this.alias = alias.toLowerCase().replaceAll(";", " ").trim().replaceAll("\\s+", "\\s");
		this.e = e;
		this.map = map;
	}
	
	String help() {
		return alias + (alias.endsWith(";") ? "" : "[;]");
	}
	
	boolean matches(String command) {
		return e.test(command);
	}
	
	String parse(String command) {
		return map.apply(command);
	}
	
	static Alias describe_table() {
		String regex = "describe\\s+table\\s+(\\w+)\\.(\\w+)";
		return new Alias("describe table <schema>.<table>", "Describe table <table> on schema <schema>",
				e -> e.matches(regex),
				e -> {
					Pattern pattern = Pattern.compile(regex);
					Matcher matcher = pattern.matcher(e);
					if (matcher.find()) {
						Object[] s = new String[matcher.groupCount()];
						for (int i=1; i<=matcher.groupCount();i++) {
							s[i-1] = matcher.group(i);
						}
						return String.format("use %s; exec sp_columns %s;", s);
					}
					return null;
				}
		);
	}
	
	static Alias show_tables() {
		return new Alias("show tables", "List tables in schema", e -> e.matches("show\\s+tables"),
			e -> "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE';");
	}
	
	static Alias show_databases() {
		return new Alias("show databases", "List databases in master DBO", e -> e.matches("show\\s+databases"),
			e -> "EXEC sp_databases;");
	}
	
	static Stream<Alias> all() {
		return Arrays.asList(describe_table(), show_databases(), show_databases()).stream();
	}
}
