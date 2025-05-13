package app.common.configuration;

import java.io.IOException;
import java.util.Properties;

public class Configuration {

    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(Configuration.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getProperty(String key) {
        return System.getenv().getOrDefault(key.toUpperCase().replace(".", "_"),
                properties.getProperty(key));
    }

}