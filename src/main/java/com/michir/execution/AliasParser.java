package com.michir.execution;

import com.michir.QueryRunner;
import com.michir.alias.AliasesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AliasParser implements Executor {

	@Autowired
	QueryRunner queryRunner;

	@Autowired
	AliasesRepository aliasesRepository;

	@Override
	public Boolean supported(String command) {
		return aliasesRepository.aliases()
				.stream()
				.filter(alias -> command.matches(alias.getPattern()))
				.findAny().isPresent();
	}

	@Override
	public void run(String command) throws Exception {
		aliasesRepository.aliases()
				.stream()
				.filter(alias -> command.matches(alias.getPattern()))
				.findFirst()
				.ifPresent(alias -> {

					String sql = alias.getSql();
					Pattern pattern = Pattern.compile(alias.getPattern());
					Matcher matcher = pattern.matcher(command);
					if (matcher.find()) {
						Object[] s = new String[matcher.groupCount()];
						for (int i=1; i<=matcher.groupCount();i++) {
							s[i-1] = matcher.group(i);
						}
						String.format(sql, s);
					}

					try {
						queryRunner.execute(sql);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
	}
	
}
