package sample.OracleConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static sample.OracleConnection.OracleConnection.getOracleConnection;


public class TestConnection {

    public static void main(String[] args)throws SQLException {

        String selectTableSQL = "select * from personnel";

        Statement statement = null;

        try{
            Connection connection= getOracleConnection();

            statement = connection.createStatement();


            //get data from db

            ResultSet rs = statement.executeQuery(selectTableSQL);



            //fetch data

            while(rs.next()){
                String field = rs.getString("NOM");

                System.out.println("field : "+field);
            }
            rs.close();




        }
        catch (SQLException e){
            System.out.println("1000000 dawa7");
        }

    }

}