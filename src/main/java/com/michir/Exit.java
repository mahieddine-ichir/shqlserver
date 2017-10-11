package com.michir;

import org.springframework.stereotype.Component;

@Component
public class Exit implements Executor {

	@Override
	public void run(String sql) {
		System.exit(0);
	}

	@Override
	public Boolean supported(String command) {
		return command.equalsIgnoreCase("exit") || command.equalsIgnoreCase("quit");
	}
}
