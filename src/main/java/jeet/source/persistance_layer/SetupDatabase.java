package jeet.source.persistance_layer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * Methodensammlung zum Erstellen von den Datenbank-Tabellen.
 */
public class SetupDatabase {
    public static void initDatabase() throws SQLException {
        DatabaseUtils.clean();
        SetupDatabase.fullSetup();
        TestData.generateTestData();
        DatabaseUtils.closeConnection();
    }

    public static void fullSetup() throws SQLException {
        fullSetup("test.db");
    }

    public static void fullSetup(String path) throws SQLException {

        if (DatabaseUtils.getConnection(path) == null) {
            System.out.println("Verbindung zur SQLite-Datenbank fehlgeschlagen");
            return;
        }

        System.out.println("Verbindung zur SQLite-Datenbank hergestellt");

        lehrveranstaltung(path);
        vorlesung(path);
        hintergrund(path);
    }

    public static void lehrveranstaltung(String path) throws SQLException {
        String queryLV = "CREATE TABLE Lehrveranstaltung (" +
                "LehrveranstaltungID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT," +
                "Zielgruppe TEXT," +
                "Semester TEXT," +
                "Autor TEXT," +
                "Datum TEXT);";

        try(PreparedStatement stmt = DatabaseUtils.getConnection(path).prepareStatement(queryLV)) {
            stmt.execute();
            DatabaseUtils.commit();
        } catch (SQLException e) {
            DatabaseUtils.rollback(e);
        }
    }

    public static void vorlesung (String path) throws SQLException {
        String queryVL = "CREATE TABLE Vorlesung (" +
                "VorlesungID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "LehrveranstaltungID INTEGER," +
                "Name TEXT," +
                "Autor TEXT," +
                "Datum TEXT," +
                "Nummer INTEGER," +
                "PDFDatei BLOB," +
                "FOREIGN KEY (LehrveranstaltungID) REFERENCES Lehrveranstaltung(LehrveranstaltungID));";

        try(PreparedStatement stmt = DatabaseUtils.getConnection(path).prepareStatement(queryVL)) {
            stmt.execute();
            DatabaseUtils.commit();
        } catch (SQLException e) {
            DatabaseUtils.rollback(e);
        }
    }

    public static void hintergrund(String path) throws SQLException {
        String query = "CREATE TABLE Hintergrund (" +
                "HintergrundID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Typ TEXT, " +
                "Farbe INTEGER, " +
                "Name TEXT, " +
                "Bild BLOB);";

        try(PreparedStatement stmt = DatabaseUtils.getConnection(path).prepareStatement(query)) {
            stmt.execute();
            DatabaseUtils.commit();
        } catch (SQLException e) {
            DatabaseUtils.rollback(e);
        }
    }

}
