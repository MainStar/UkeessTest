package ukeesTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ukeesTest.dao.DataFactory;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
public class App
{
    public static void main( String[] args ) throws SQLException, ClassNotFoundException, IOException {
        DataFactory.connectToUkeessTestTable();
        SpringApplication.run(App.class, args);
    }
}
