package datahandeler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class propertiesConfig {

    //load the data from properties file

    public static Properties loginUserData= loadProperties("src/main/resources/loginUserData.properties");

    private static Properties loadProperties(String filePath){
        Properties pro= new Properties();
        //stream for reading file
        FileInputStream stream= null;
        try {
            stream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            pro.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return pro;
    }
}
