package com.kuvshinov.graph.server.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Sergey Kuvshinov
 */
public class PropertyReader {

    private Properties properties;


    public PropertyReader(InputStream file) throws IOException {
        properties = new Properties();
        properties.load(file);
        file.close();
    }

    public PropertyReader(String fileName) throws IOException {
        this(PropertyReader.class.getClassLoader().getResourceAsStream(fileName));
    }

    public String getProperty(String name) {
        return properties.getProperty(name, null);
    }

    public int getPropertyAsInt(String name) {
        return Integer.parseInt(properties.getProperty(name));
    }

}
