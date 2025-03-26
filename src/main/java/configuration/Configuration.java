package configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Configuration.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
                System.out.println("Config loaded successfully");
            } else {
                System.err.println("config.properties NOT FOUND in classpath!");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        System.out.println("Requested key: " + key + " -> Value: " + value);
        return value;
    }
}