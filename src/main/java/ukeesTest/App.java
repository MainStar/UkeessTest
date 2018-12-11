package ukeesTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ukeesTest.dao.DataFactory;

import java.sql.SQLException;

@SpringBootApplication
public class App
{
    public static void main( String[] args ) throws SQLException, ClassNotFoundException {
        DataFactory.connectToUkeessTestTable();
        SpringApplication.run(App.class, args);
    }
}
