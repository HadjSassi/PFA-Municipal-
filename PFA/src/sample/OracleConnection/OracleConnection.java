package sample.OracleConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class OracleConnection {

    public static Connection getOracleConnection() throws SQLException{
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
        }
        catch (ClassNotFoundException ex){
            System.out.println("error : unable to load driver class!");
        }
        catch (IllegalAccessError ex){
            System.out.println("error : access problem while loading!");
        } catch (IllegalAccessException e) {
            System.out.println("error 10");
        } catch (InstantiationException e) {
            System.out.println("error 100");
        }

        String URL = "jdbc:oracle:thin:@localhost:1521/XE";
        String Username = "test";
        String Password = "test";

        Connection connection = null ;

        try{
            connection = DriverManager.getConnection(URL,Username,Password);
        }
        catch (SQLException e){
            System.out.println("error 1000");
            System.out.println(e.getErrorCode());
        }

        return connection;
    }

}