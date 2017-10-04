package com.michir;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		List<String> history = new ArrayList<>();
		
		SQLCommandRunner runner = context.getBean(SQLCommandRunner.class);
		SQLCommandsHelper helper = context.getBean(SQLCommandsHelper.class);
		SQLCommandAliasParser aliasParser = context.getBean(SQLCommandAliasParser.class);

		while (true) {
			
			Scanner scanner = new Scanner(System.in);
			System.out.print("sql> ");
			String sql = scanner.nextLine();

			LogFactory.getLog(Application.class).debug("Executing "+sql);
			
			if (sql.equals("exit")) {
				scanner.close();
				System.exit(0);
			}
			
			if (sql.equals("help")) {
				helper.run();
			} else {
				if (aliasParser.contains(sql))
					sql = aliasParser.parse(sql);
				try {
					runner.run(sql);
					history.add(sql);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
	}
}

