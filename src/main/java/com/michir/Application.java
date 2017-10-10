package com.michir;

import java.util.Scanner;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

	private static final Log LOG = LogFactory.getLog(Application.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		Stream<Executor> executors = context.getBeansOfType(Executor.class).values().stream();

		// to capture User inputs
		Scanner scanner = new Scanner(System.in);
		// to execute 
		Stream<Executor> queryRunner = Stream.of(context.getBean(QueryRunner.class));

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				scanner.close();
				queryRunner.close();
			}
		});

		while (true) {

			String sql = scanner.nextLine().trim();

			LOG.debug("Executing "+sql);
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

