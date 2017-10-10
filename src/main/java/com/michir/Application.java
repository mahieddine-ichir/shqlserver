package com.michir;

import java.util.Map;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

	static Scanner scanner = null;

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		Map<String, Executor> executors = context.getBeansOfType(Executor.class);

		QueryRunner runner = context.getBean(QueryRunner.class);
		Use use = context.getBean(Use.class);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				if (scanner != null) {
					scanner.close();
				}
			}
		});

		while (true) {
			scanner = new Scanner(System.in);
			use.onStart(); // FIXME remove using a start event

			String sql = scanner.nextLine().trim();
			if (sql.isEmpty()) {
				continue;
			}

			if (executors.values().stream().anyMatch(e -> e.supported(sql))) {
				executors.values().stream().filter(e -> e.supported(sql)).forEach(e -> {
					try {
						e.run(sql);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				});
			} else {
				try {
					runner.execute(sql);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}

