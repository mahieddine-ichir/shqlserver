package com.michir;

import java.util.Scanner;
import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

	static Scanner scanner = null;

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		Stream<Executor> executors = context.getBeansOfType(Executor.class).values().stream();

		Stream<Executor> queryRunner = Stream.of(context.getBean(QueryRunner.class));
		Stream<Use> use = Stream.of(context.getBean(Use.class));

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				if (scanner != null) {
					scanner.close();
				}
				queryRunner.close();
			}
		});

		while (true) {
			scanner = new Scanner(System.in);
			use.forEach(e -> e.onStart());

			String sql = scanner.nextLine().trim();
			if (sql.isEmpty()) {
				continue;
			}

			Stream<Executor> execs;
			if (executors.anyMatch(e -> e.supported(sql))) {
				execs = executors.filter(e -> e.supported(sql));
			} else {
				execs = queryRunner;
			}
			execs.forEach(e -> {
				try {
					e.run(sql);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
		}
	}
}

