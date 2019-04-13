package com.michir.alias;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class AliasesRepository {

  private Collection<Alias> aliases = new ArrayList<>();

  @PostConstruct
  void init() {
    aliases.add(
      Alias.builder()
        .usage("describe table schema..table")
        .pattern("describe\\s+table\\s+(\\w+)\\.{1,2}(\\w+)\\s*;*")
        .sql("use %s; exec sp_columns %s;")
        .description("Describe table <table> on schema <schema>")
        .build()
    );

    aliases.add(
      Alias.builder()
        .usage("show tables")
        .pattern("show tables;*")
        .sql("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE';")
        .description("List tables in schema")
        .build()
    );
  }

  public Collection<Alias> aliases() {
    return aliases;
  }

}
