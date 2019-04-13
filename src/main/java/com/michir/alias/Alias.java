package com.michir.alias;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Alias {

  private String pattern;

  private String sql;

  private String usage;

  private String description;
}
