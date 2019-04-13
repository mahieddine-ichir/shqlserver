package com.michir;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class QueryRunner {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	private String context = "";
	
	public void execute(String sql, Boolean use) throws Exception {
		if (use && !context.isEmpty() && !sql.toLowerCase().startsWith("use ")) {
			sql = String.format("use %s;", context)+sql;
		}
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

		publisher.publishEvent(new QueryEvent(sql));
	}

	public void execute(String sql) throws Exception {
		this.execute(sql, true);
	}

	@EventListener
	public void onSqlContextChanged(SqlContextChangedEvent event) {
		this.context = event.getContext();
	}
}
