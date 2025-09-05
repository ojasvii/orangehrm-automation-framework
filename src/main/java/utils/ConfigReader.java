package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties = new Properties();


    /**
     * Loads config file based on the environment.
     * Example:
     * mvn test -Denvironment=staging
     */

    public static void loadConfig(String env){

        try {
//             Default environment
            if (env == null || env.isEmpty()) {
                System.out.println("Environment not set, defaulting to QA");
                env = "qa";
            }

            String path = "src/main/resources/config-" + env.toLowerCase() + ".properties";
//            String path = "src/main/resources/config-qa.properties";
            System.out.println("Loading config file:" + path);

            FileInputStream fis = new FileInputStream(path);
            properties.load(fis);



        }catch(IOException e){
            e.printStackTrace();
            throw new RuntimeException("Config file not found for env: " + env);
        }


    }

    public static String getProperty(String key){
        String value = properties.getProperty(key);
        if(value==null){
            throw new RuntimeException("Property " + key + " not found in config file");
        }
        return value;
    }

}
