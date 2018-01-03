package com.michir;

import org.springframework.stereotype.Component;

@Component
public class SQLCommandsHelper {

	public void run() {
		System.out.println("================");
		Alias.all().forEach(a -> a.help());
		System.out.println("================");
	}
}
