package com.michir;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SQLCommandRunner {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void run(String sql) throws Exception {
		List<Map<String,Object>> query = jdbcTemplate.query(sql, new ColumnMapRowMapper());
		
		Map<String, List<Object>> collect = query.stream()
			.flatMap(e -> e.entrySet().stream())
			.collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
		
		System.out.println(
				collect.keySet().stream().collect(Collectors.joining(" | "))
				);
		System.out.println("<--------------------->");
		query.forEach(e -> {
			System.out.println(e.values().stream().map(m -> ""+m).collect(Collectors.joining(" | ")));	
		});
		
	}
}
