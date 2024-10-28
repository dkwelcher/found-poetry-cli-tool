package config;

import org.apache.xmlbeans.impl.xb.xmlconfig.ConfigDocument;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ModelFilePathConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream inputStream = ConfigDocument.Config.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream == null) {
                throw new FileNotFoundException("application.properties file not found");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            System.err.println("Unable to load application.properties file.");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}