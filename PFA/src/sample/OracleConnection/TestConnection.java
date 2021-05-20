package sample.OracleConnection;

import sample.App.model.Compte;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static sample.OracleConnection.OracleConnection.getOracleConnection;


public class TestConnection {

    public static void main(String[] args)throws SQLException {
        try {
        Connection connection= getOracleConnection();
        ResultSet rs = connection.createStatement().executeQuery("select * from COMPTE ");
        while(rs.next()){
            // oblist.add(new Compte(rs.getString("cin"),rs.getString("pass"),""));
            System.out.println(rs.getString("MATRICULE"));
        }
        rs.close();
    } catch (SQLException throwables) {
               System.out.println(throwables);
           }

    }

}