package com.michir.execution;

import com.michir.alias.AliasesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Help implements Executor {

	@Autowired
	AliasesRepository aliasesRepository;

	@Override
	public void run(String sql) {
		System.out.println("================");
		aliasesRepository.aliases()
				.forEach(alias -> System.out.println(String.format("\t%s \t%s", alias.getUsage(), alias.getDescription())));
		System.out.println("================");
	}

	@Override
	public Boolean supported(String command) {
		return command.equalsIgnoreCase("help") || command.equals("?");
	}
}
