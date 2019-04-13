package com.michir.execution;

import com.michir.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FileExecutor implements Executor {

  @Autowired
  QueryRunner queryRunner;

  @Override
  public Boolean supported(String command) {
    return command.startsWith("--file");
  }

  @Override
  public void run(String sql) throws Exception {
    String buffer = Files.readString(Paths.get(sql.substring("--file".length()).trim()));
    queryRunner.execute(buffer);
  }
}
