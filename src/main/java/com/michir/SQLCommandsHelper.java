package com.michir;

import org.springframework.stereotype.Component;

@Component
public class SQLCommandsHelper {

	public void run() {
		System.out.println("================");
		Alias.all().map(Alias::help).forEach(e -> System.out.println(e));
		System.out.println("================");
	}
}
