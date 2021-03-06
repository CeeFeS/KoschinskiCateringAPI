package de.cfschulze.KoschinskiCateringAPI.database;

import de.cfschulze.KoschinskiCateringAPI.model.Gericht;
import de.cfschulze.KoschinskiCateringAPI.model.Menu;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBController {
    private static final DBController dbcontroller = new DBController();
    private static Connection connection;
    private static final String DB_PATH = "./" + "meals.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Fehler beim Laden des JDBC-Treibers");
            e.printStackTrace();
        }
    }

    private DBController() {
    }

    public static DBController getInstance() {
        return dbcontroller;
    }

    public void initDBConnection() {
        try {
            if (connection != null)
                return;
            System.out.println("Creating Connection to Database...");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            if (!connection.isClosed())
                System.out.println("...Connection established");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    if (!connection.isClosed() && connection != null) {
                        connection.close();
                        if (connection.isClosed())
                            System.out.println("Connection to Database closed");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void createTable() throws SQLException {

        Statement stmt = connection.createStatement();

        try {
            stmt.executeUpdate("CREATE TABLE meals (tag, datum, gericht);");
        } catch (Exception e) {
            System.out.println("Table already exists");
        }

    }

    public void insertMenus(ArrayList<Menu> menus) {
        try {
            Statement stmt = connection.createStatement();

            for (Menu menu : menus) {
                for (String gericht : menu.getGerichte()) {
                    stmt.execute(" DELETE FROM meals WHERE tag == '" + menu.getTag() + "' AND datum == '" + menu.getDatum() + "' AND gericht == '" + gericht + "'");
                    stmt.execute("INSERT INTO meals (tag, datum, gericht) VALUES ('" + menu.getTag() + "', '" + menu.getDatum() + "', '" + gericht + "')");
                }
            }

        } catch (SQLException e) {
            System.err.println("Couldn't handle DB-Query");
            e.printStackTrace();
        }
    }

    public ArrayList<Gericht> getAll() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM meals;");
        ArrayList<Gericht> gerichte = new ArrayList<>();
        while (rs.next()) {
            Gericht gericht = new Gericht();
            gericht.setTag(rs.getString("tag"));
            gericht.setDatum(rs.getString("datum"));
            gericht.setBeschreibung(rs.getString("gericht"));
            gerichte.add(gericht);
        }
        rs.close();
        //connection.close();
        return gerichte;
    }
}
