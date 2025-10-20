package edu.bbte.idde.dkim2226.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class ConfigurationFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationFactory.class);

    @Getter
    private static MainConfiguration mainConfiguration = new MainConfiguration();

    static {
        String configFile = "/application";

        ObjectMapper objectMapper = new ObjectMapper();

        String profile = System.getenv("PROFILE");

        configFile = configFile + '-' + profile + ".json";

        // cfg file, env var alapjan ( configFile FELEPITVE )
        try (InputStream inputStream = ConfigurationFactory.class.getResourceAsStream(configFile)) {
            mainConfiguration = objectMapper.readValue(inputStream, MainConfiguration.class);
            LOG.info("Read following configuration: {}", mainConfiguration);
        } catch (IOException e) {
            LOG.error("Error loading configuration", e);
        }
    }
}
