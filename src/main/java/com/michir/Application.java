package com.michir;

import com.michir.execution.Executor;
import com.michir.execution.Use;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;
import java.util.Scanner;

@Slf4j
@SpringBootApplication
public class Application {

	static Scanner scanner = null;

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		Map<String, Executor> executors = context.getBeansOfType(Executor.class);
		QueryRunner runner = context.getBean(QueryRunner.class);
		Use use = context.getBean(Use.class);

		scanner = new Scanner(System.in);
		Runtime.getRuntime().addShutdownHook(new Thread(scanner::close));

		while (true) {
			use.next();

			String sql = scanner.nextLine().trim();
			if (sql.isEmpty()) {
				continue;
			}

			executors.entrySet()
					.stream()
					.map(Map.Entry::getValue)
					.filter(executor -> executor.supported(sql.trim()))
					.findAny()
					.ifPresentOrElse(executor -> {
						log.debug("Using '{}' for command '{}'", executor, sql);
						try {
							executor.run(sql);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}, () -> {
						try {
							runner.execute(sql);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					});
		}
	}
}

