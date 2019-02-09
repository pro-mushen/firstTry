package ru.tander.config;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static final String URL_DB = "db.url";
    public static final String LOGIN_DB = "db.login";
    public static final String PASSWORD_DB = "db.password";
    public static final String MAX_QUERY_ONE_BATCH_DB = "db.maxQueryOneBatch";
    public static final String ORIGINAL_XML = "xml.originalXml";
    public static final String TRANSFORM_XML = "xml.transformXml";
    public static final String XSL = "xml.xsl";


    private static final String NAME_PROPERTIES_FILE = "allData.properties";
    private static final Logger LOGGER = Logger.getLogger(Config.class);
    private static Properties properties = new Properties();

    private Config() {
    }

    public static String getProperty(String name) {
        if (properties.isEmpty()) {
            try {
                try (InputStream is = Config.class.getClassLoader()
                        .getResourceAsStream(NAME_PROPERTIES_FILE)) {
                    properties.load(is);
                }
            } catch (IOException e) {
                LOGGER.error("Could not read data from file '" + NAME_PROPERTIES_FILE + "'. " + e);
            }
        }
        return properties.getProperty(name);
    }
}
