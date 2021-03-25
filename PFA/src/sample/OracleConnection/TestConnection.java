package sample.OracleConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static sample.OracleConnection.OracleConnection.getOracleConnection;


public class TestConnection {

    public static void main(String[] args)throws SQLException {

        String selectTableSQL = "select * from Tache";

        Statement statement = null;

        try{
            Connection connection= getOracleConnection();

            statement = connection.createStatement();


            //get data from db

            ResultSet rs = statement.executeQuery(selectTableSQL);



            //fetch data

            while(rs.next()){
                String field = rs.getString("IdTache");
                String field1 = rs.getString("NomTache");
                String field2 = rs.getString("DescriptionTache");


                System.out.println("field : "+field+ field1 + field2);
            }
            rs.close();




        }
        catch (SQLException e){
            System.out.println("1000000 dawa7");
        }

    }

}