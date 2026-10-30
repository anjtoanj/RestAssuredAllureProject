package com.pets.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigBuilder {
    private static  final Properties props = new Properties();

    static {
        String envProperties = "env/" + System.getProperty("env") + ".properties";

        try (InputStream is =ConfigBuilder.class.getClassLoader().getResourceAsStream(envProperties)){
            if (is == null) throw new RuntimeException("Missing env file: " + envProperties);
            try {
                props.load(is);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load com.gsb.config common env file "+e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

     public static String getEnvironment(){
        String env = System.getProperty("env");
        return env;
     }

    public static String getBaseUrl(){ return props.getProperty("baseUrl"); }

    public static String getHostname(){ return props.getProperty("hostname"); }

    public static String getHttpScheme(){
        return props.getProperty("hostname").equals("localhost") ? "http" : "https"; }

}
