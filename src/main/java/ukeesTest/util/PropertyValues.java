package ukeesTest.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertyValues {

    private static Properties properties = new Properties();

    public static List<String> getDBPropertyValues() throws IOException {
        List<String> values = new ArrayList<>();
        FileInputStream fileProperties = new FileInputStream("src/main/resources/database.properties");
        properties.load(fileProperties);
        values.add(properties.getProperty("db.ClassForName"));
        values.add(properties.getProperty("db.host"));
        values.add(properties.getProperty("db.login"));
        values.add(properties.getProperty("db.password"));
        return values;
    }

}
