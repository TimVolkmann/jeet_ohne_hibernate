package jeet.source.persistance_layer;

import java.io.File;
import java.sql.*;
/*
 * Klasse, die alles rund um die Datenbank-Verbindung regelt. 'connection' wird als Singleton behandelt.
 * Mit 'path' um zwischen Datenbanken wählen zu können; ohne ist Standard während der Entwicklung.
 * 'clean(...)' löscht DB. 'commit()' & 'rollback()' delegieren Funktionalität.
 */
public class DatabaseUtils {

    private static final String DRIVER = "jdbc:sqlite:";
    private static final String DB_PATH = "src/main/resources/database/";
    private static String concrete_database = "test.db";
    private static Connection connection = null;

    //------------------------------------------------------------------------------------------------------------------

    public static Connection getConnection(String path) throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(DRIVER + DB_PATH + path);
            connection.setAutoCommit(false);
        }
        return connection;
    }

    public static Connection getConnection() throws SQLException {
        return getConnection(concrete_database);
    }

    public static void closeConnection() throws  SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    public static void clean() {
        clean("test.db");
    }

    public static void clean(String path) {
        File myObj = new File(DB_PATH + path);
        myObj.delete();
    }

    //------------------------------------------------------------------------------------------------------------------

    public static void commit() throws SQLException {
        connection.commit();
    }

    public static void rollback(SQLException e) throws RuntimeException, SQLException {
        connection.rollback();
        handleException(e);
    }

    public static void handleException(Exception e) throws RuntimeException {
        e.printStackTrace();
        throw new RuntimeException(e);
    }

}
