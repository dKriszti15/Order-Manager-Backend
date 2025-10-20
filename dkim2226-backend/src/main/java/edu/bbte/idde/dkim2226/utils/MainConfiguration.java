package edu.bbte.idde.dkim2226.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MainConfiguration {
    private String dao;
    @JsonProperty("jdbc")
    private JdbcConfiguration jdbcConfiguration = new JdbcConfiguration();
}
