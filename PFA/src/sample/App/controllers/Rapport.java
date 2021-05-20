package sample.App.controllers;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


public abstract class Rapport {


    private static JasperReport jreport;
    private static JasperViewer jviewer;
    private static JasperPrint jprint;

    public static void createReport(Connection connect, Map<String, Object> map, InputStream by) {
        try {


            jreport = (JasperReport)JRLoader.loadObject(by);
            jprint = JasperFillManager.fillReport(jreport, map, connect);

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void genReport(Connection connect, Map<String, Object> map, String query,String filejrxml) throws ClassNotFoundException, SQLException {
        try {
            JasperDesign jdesign = JRXmlLoader.load(filejrxml);

            JRDesignQuery updateQuery = new JRDesignQuery();
            updateQuery.setText(query);

            jdesign.setQuery(updateQuery);

            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, null , connect);

            jviewer = new JasperViewer(jprint, false); // false to avoid closing the main application and will only close the print preview
            jviewer.setVisible(true);

        } catch(JRException e) {
            e.printStackTrace();
        }
    }

    public static void showReport() {
        jviewer = new JasperViewer(jprint, false); // false to avoid closing the main application and will only close the print preview
        jviewer.setVisible(true);
    }





}

