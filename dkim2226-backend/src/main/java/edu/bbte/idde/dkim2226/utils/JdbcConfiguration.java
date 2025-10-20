package edu.bbte.idde.dkim2226.utils;

import lombok.Data;

@Data
public class JdbcConfiguration {
    private Boolean createTables = false;
    private String driverClass;
    private String url;
    private String username;
    private String password;
    private Integer poolSize;
}
