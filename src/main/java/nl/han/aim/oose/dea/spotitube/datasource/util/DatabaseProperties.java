package nl.han.aim.oose.dea.spotitube.datasource.util;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProperties {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final Properties properties;

    public DatabaseProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Can't access property file database.properties", e);
        }
    }

    public String connectionString() {
        return properties.getProperty("connectionString");
    }

}
