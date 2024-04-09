package utils;

import lombok.extern.java.Log;
import java.io.*;
import java.util.Properties;

@Log
public class PropertyReader {
    private static Properties properties;

    public PropertyReader(String fileName) {
        properties = appendFromResource(fileName);
    }
    private Properties appendFromResource(String resourceName) {
        Properties properties = new Properties();

        try (InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);) {
            properties.load(inStream);
        } catch (IOException e) {
            log.warning("файл со свойствами пуст");
            log.warning(e.getMessage());
        }
        return properties;
    }
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    public long getLongProperty(String key) {
        return Long.parseLong(properties.getProperty(key));
    }
    public void setValue(String key, String value){
        properties.setProperty(key, value);
    }
}