package sample.App.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sample.OracleConnection.OracleConnection.getOracleConnection;

public class Statistics implements Initializable {

    @FXML
    private PieChart pieChart;

    @FXML
    private Button boutonRapportActivite;

    @FXML
    private Button boutonRapportFinancier;


    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private Pagination page ;

    private Map<String, Object> map;



    private int year(String date){

        return Integer.parseInt(date.charAt(0)+date.charAt(1)+date.charAt(2)+date.charAt(3)+"");
    }


    private int mois(String date){

        return Integer.parseInt(date.charAt(5)+date.charAt(6)+"");
    }


    @FXML
    void rapportActiviteBouton(ActionEvent event) {

    }

    @FXML
    void rapportFinancierBouton(ActionEvent event) throws  FileNotFoundException {
        try {
            Connection connect = getOracleConnection();
            map = new HashMap<String, Object>();

//            InputStream initialStream = new FileInputStream(new File("C:\\Users\\Wissal\\JaspersoftWorkspace\\MyReports\\exemple.jasper"));
            InputStream initialStream = new FileInputStream(new File("C:\\Users\\Kerkennah\\Desktop\\PFA\\src\\rapports\\rapportF.jasper"));

            Rapport.createReport(connect, map, initialStream);
            Rapport.showReport();
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        page.setPageFactory((pageIndex)->{
            if (pageIndex == 0) {
                lineChart.getData().clear();
                XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
                XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
                try {
                    Connection connection = getOracleConnection();
                    ResultSet rs = connection.createStatement().executeQuery("select * from depense order by Dates");
                    while (rs.next()) {
                        series.getData().add(new XYChart.Data<String, Number>(rs.getDate("dates").toString(), Double.parseDouble(rs.getString("prix"))));
                    }
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    Connection connection = getOracleConnection();
                    ResultSet rs = connection.createStatement().executeQuery("select * from revenu order by Dates");
                    while (rs.next()) {
                        series1.getData().add(new XYChart.Data<String, Number>(rs.getDate("dates").toString(), Double.parseDouble(rs.getString("prix"))));
                    }
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


                series.setName("Depenses");
                series1.setName("Revenues");
                lineChart.getXAxis().setLabel("Dates");
                lineChart.getYAxis().setLabel("Prix");
                lineChart.getData().add(series);
                lineChart.getData().add(series1);
                lineChart.setTitle("Depenses VS Revenues");

                int depense = 0, revenu = 0;

                try {
                    Connection connection = getOracleConnection();
                    ResultSet rs = connection.createStatement().executeQuery("select count(*) from Revenu ");
                    while (rs.next()) {
                        String ch = rs.getString("count(*)");
                        depense = Integer.parseInt(ch);
                    }
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                try {
                    Connection connection = getOracleConnection();
                    ResultSet rs = connection.createStatement().executeQuery("select count(*) from depense ");
                    while (rs.next()) {
                        String ch = rs.getString("count(*)");
                        revenu = Integer.parseInt(ch);
                    }
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


                ObservableList<Data> list = FXCollections.observableArrayList(
                        new PieChart.Data("Depense", depense),
                        new PieChart.Data("Revenue", revenu)
                );
                pieChart.setTitle("Depense VS Revenue");
                pieChart.setData(list);
            }
            else if (pageIndex == 1){
                {

                    lineChart.getData().clear();
                    XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
                    try {
                        Connection connection = getOracleConnection();
                        ResultSet rs = connection.createStatement().executeQuery("select * from materiel");
                        while (rs.next()) {
                            series.getData().add(new XYChart.Data<String, Number>(rs.getString("designation"), Integer.parseInt(rs.getString("qte"))));
                        }
                        rs.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    series.setName("Materiel en fonction de sa quantité");

                    lineChart.getXAxis().setLabel("Designation");
                    lineChart.getYAxis().setLabel("Quantité");
                    lineChart.setTitle("Materiel");
                    lineChart.getData().add(series);

                    int depense = 0, revenu = 0;

                    try {
                        Connection connection = getOracleConnection();
                        ResultSet rs = connection.createStatement().executeQuery("select count(*) from engin where Dispo = 'Oui' ");
                        while (rs.next()) {
                            String ch = rs.getString("count(*)");
                            depense = Integer.parseInt(ch);
                        }
                        rs.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    try {
                        Connection connection = getOracleConnection();
                        ResultSet rs = connection.createStatement().executeQuery("select count(*) from engin where dispo = 'Non' ");
                        while (rs.next()) {
                            String ch = rs.getString("count(*)");
                            revenu = Integer.parseInt(ch);
                        }
                        rs.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }


                    ObservableList<Data> list = FXCollections.observableArrayList(
                            new PieChart.Data("Engin en garage", depense),
                            new PieChart.Data("Engin En execution", revenu - depense)
                    );

                    pieChart.setTitle("Engin");

                    pieChart.setData(list);
                }
            }
            else if (pageIndex == 2){
                {

                    lineChart.getData().clear();
                    XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
                    XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
                    try {
                        Connection connection = getOracleConnection();
                        ResultSet rs = connection.createStatement().executeQuery("select * from doleance");
                        while (rs.next()) {
                            series.getData().add(new XYChart.Data<String, Number>(rs.getString("Status"), mois((String.valueOf(rs.getDate("dates"))))));
                        }
                        rs.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    try {
                        Connection connection = getOracleConnection();
                        ResultSet rs = connection.createStatement().executeQuery("select * from PERMISSION");
                        while (rs.next()) {
                            series1.getData().add(new XYChart.Data<String, Number>(rs.getString("Status"), mois((String.valueOf(rs.getDate("dates"))))));
                        }
                        rs.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }


                    series.setName("Doleance");
                    series1.setName("Permissions");
                    lineChart.getXAxis().setLabel("Status");
                    lineChart.getYAxis().setLabel("Mois");
                    lineChart.getData().add(series);
                    lineChart.getData().add(series1);
                    lineChart.setTitle("Doleance VS Permissions");

                    int i = 0, e = 0 , a = 0 , r = 0 , t = 0;


                    try {
                        Connection connection = getOracleConnection();
                        ResultSet rs = connection.createStatement().executeQuery("select count(*) from doleance where status = 'Initiale' ");
                        while (rs.next()) {
                            String ch = rs.getString("count(*)");
                            i = Integer.parseInt(ch);
                        }
                        rs.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    try {
                        Connection connection = getOracleConnection();
                        ResultSet rs = connection.createStatement().executeQuery("select count(*) from doleance where status = 'EnCours'");
                        while (rs.next()) {
                            String ch = rs.getString("count(*)");
                            e = Integer.parseInt(ch);
                        }
                        rs.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    try {
                        Connection connection = getOracleConnection();
                        ResultSet rs = connection.createStatement().executeQuery("select count(*)  from doleance where status = 'Approuvé'");
                        while (rs.next()) {
                            String ch = rs.getString("count(*)");
                            a = Integer.parseInt(ch);
                        }
                        rs.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    try {
                        Connection connection = getOracleConnection();
                        ResultSet rs = connection.createStatement().executeQuery("select count(*)  from doleance where status = 'Refusé'");
                        while (rs.next()) {
                            String ch = rs.getString("count(*)");
                            r = Integer.parseInt(ch);
                        }
                        rs.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    try {
                        Connection connection = getOracleConnection();
                        ResultSet rs = connection.createStatement().executeQuery("select count(*) from doleance where status = 'Terminé'");
                        while (rs.next()) {
                            String ch = rs.getString("count(*)");
                            t = Integer.parseInt(ch);
                        }
                        rs.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }


                    ObservableList<Data> list1 = FXCollections.observableArrayList(
                            new PieChart.Data("Nombre de Doleance intiale", i),
                            new PieChart.Data("Nombre de Permission en cours", e),
                            new PieChart.Data("Nombre de Permission apourvé" , a),
                            new PieChart.Data("Nombre de Permission refusé", r),
                            new PieChart.Data("Nombre de Permission terminé", t)
                    );

                    pieChart.setTitle("Doleance");

                    pieChart.setData(list1);

                    int depense = 0, revenu = 0;

                    try {
                        Connection connection = getOracleConnection();
                        ResultSet rs = connection.createStatement().executeQuery("select count(*) from doleance ");
                        while (rs.next()) {
                            String ch = rs.getString("count(*)");
                            depense = Integer.parseInt(ch);
                        }
                        rs.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    try {
                        Connection connection = getOracleConnection();
                        ResultSet rs = connection.createStatement().executeQuery("select count(*) from PERMISSION ");
                        while (rs.next()) {
                            String ch = rs.getString("count(*)");
                            revenu = Integer.parseInt(ch);
                        }
                        rs.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }


                    ObservableList<Data> list = FXCollections.observableArrayList(
                            new PieChart.Data("Nombre de Doleance", depense),
                            new PieChart.Data("Nombre de Permission", revenu)
                    );

                    pieChart.setTitle("Doleance VS Permission");

                    pieChart.setData(list);
                }
            }
            else {
                {

                    lineChart.getData().clear();
                    XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
                    XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
                    try {
                        Connection connection = getOracleConnection();
                        ResultSet rs = connection.createStatement().executeQuery("select * from Personnel");
                        while (rs.next()) {
                            series.getData().add(new XYChart.Data<String, Number>(rs.getString("service"), (rs.getDouble("salaire"))));
                        }
                        rs.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }


                    series.setName("Personnel");
                    lineChart.getXAxis().setLabel("Service");
                    lineChart.getYAxis().setLabel("Salaire");
                    lineChart.getData().add(series);
                    lineChart.setTitle("Personnel");

                    int depense = 0, revenu = 0;

                    try {
                        Connection connection = getOracleConnection();
                        ResultSet rs = connection.createStatement().executeQuery("select count(*) from compte ");
                        while (rs.next()) {
                            String ch = rs.getString("count(*)");
                            depense = Integer.parseInt(ch);
                        }
                        rs.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    try {
                        Connection connection = getOracleConnection();
                        ResultSet rs = connection.createStatement().executeQuery("select count(*) from personnel ");
                        while (rs.next()) {
                            String ch = rs.getString("count(*)");
                            revenu = Integer.parseInt(ch);
                        }
                        rs.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }


                    ObservableList<Data> list = FXCollections.observableArrayList(
                            new PieChart.Data("Comptes", depense),
                            new PieChart.Data("Personnel sans comptes", revenu - depense)
                    );

                    pieChart.setTitle("Personnel(Compte)");

                    pieChart.setData(list);
                }
            }


            return new VBox();
        });


    }
}
