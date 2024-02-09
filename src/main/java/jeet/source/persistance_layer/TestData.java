package jeet.source.persistance_layer;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/*
 * Methodensammlung zum Erstellen von Testdaten in der Datenbank.
 */
public class TestData {
    public static void generateTestData() throws SQLException {
        generateTestData("test.db");
    }

    public static void generateTestData(String path) throws SQLException {
        generateLehrveranstaltung(path);
        generateVorlesung(path);
        generateHintergrund(path);
    }

//----------------------------------------------------------------------------------------------------------------------

    public static void generateLehrveranstaltung(String path) throws SQLException {

        String query = "INSERT INTO Lehrveranstaltung (Name, Zielgruppe, Semester, Autor, Datum) " +
                "Values (?, ?, ?, ?, ?)";

        String[] LV_Namen       = {"TheoInf",   "Prog3",     "SE2",       "Statistik", "DBS2",      "Mathe3"};
        String[] LV_Zielgruppen = {"Bin1",      "Bin3",      "Bin5",      "Bin2/Mdi2", "Bin3",      "Bin3"};
        String[] LV_Semester    = {"WiSe21/22", "WiSe22/23", "WiSe23/24", "SoSe22",    "WiSe22/23", "WiSe22/23"};
        String[] LV_Autoren     = {"Sprengel",  "Peine",     "Bruns",     "Ahlers",    "Heine",     "Pigors"};
        String[] LV_Datum       = { LocalDate.of(2021, 12,  1).toString(),
                LocalDate.of(2022, 11, 16).toString(),
                LocalDate.of(2023, 10, 31).toString(),
                LocalDate.of(2022, 3,  14).toString(),
                LocalDate.of(2022, 9,   9).toString(),
                LocalDate.of(2022, 12,  1).toString()};

        try (PreparedStatement LV_stmt = DatabaseUtils.getConnection(path).prepareStatement(query)) {
            for (long i=0L; i<=5L; i++) {
                LV_stmt.setString(1, LV_Namen[(int) i]);
                LV_stmt.setString(2, LV_Zielgruppen[(int) i]);
                LV_stmt.setString(3, LV_Semester[(int) i]);
                LV_stmt.setString(4, LV_Autoren[(int) i]);
                LV_stmt.setString(5, LV_Datum[(int) i]);

                LV_stmt.execute();
                DatabaseUtils.commit();
            }

        } catch (SQLException e) {
            DatabaseUtils.rollback(e);
        }
    }

    public static void generateVorlesung(String path) throws SQLException {

        String id_query = "SELECT LehrveranstaltungID " +
                "FROM Lehrveranstaltung " +
                "WHERE Name = ?";
        long[]   VL_LehrveranstaltungID = new long[5];
        String[] Name_to_ID             = {"TheoInf", "TheoInf", "TheoInf", "Statistik", "Statistik"};

        String query = "INSERT INTO Vorlesung (LehrveranstaltungID, Name, Autor, Datum, Nummer) " +
                "Values ( ?, ?, ?, ?, ?)";
        String[] VL_Namen   = {"TheoInf_1", "TheoInf_2", "TheoInf_3", "Statistik_1", "Statistik_2"};
        String[] VL_Autoren = {"Sprengel",  "Sprengel",  "Hovestadt", "Ahlers",      "Heine"};
        String[] VL_Daten   = { LocalDate.of(2021, 12,  1).toString(),
                LocalDate.of(2021, 12,  8).toString(),
                LocalDate.of(2021, 12, 15).toString(),
                LocalDate.of(2022, 3,  14).toString(),
                LocalDate.of(2022, 3,  21).toString()};
        int[] VL_Nummern    = {1, 2, 3, 1, 2};

        try (PreparedStatement ID_stmt = DatabaseUtils.getConnection(path).prepareStatement(id_query)) {
            for (long i=0L; i<=4L; i++) {
                ID_stmt.setString(1, Name_to_ID[(int) i]);;

                ResultSet set = ID_stmt.executeQuery();
                VL_LehrveranstaltungID[(int) i] = set.getLong(1);
                DatabaseUtils.commit();
            }

        } catch (SQLException e) {
            DatabaseUtils.rollback(e);
        }

        try (PreparedStatement VL_stmt = DatabaseUtils.getConnection(path).prepareStatement(query)) {

            for (long i=0L; i<=4L ; i++) {
                VL_stmt.setLong(1, VL_LehrveranstaltungID[(int) i]);
                VL_stmt.setString(2, VL_Namen[(int) i]);
                VL_stmt.setString(3, VL_Autoren[(int) i]);
                VL_stmt.setString(4, VL_Daten[(int) i]);
                VL_stmt.setInt(5, VL_Nummern[(int) i]);

                VL_stmt.execute();
                DatabaseUtils.commit();
            }

        } catch (SQLException e) {
            DatabaseUtils.rollback(e);
        }
    }

    public static void generateHintergrundBild(String path) throws SQLException {

        String query = "INSERT INTO Hintergrund (Typ, Name, Bild) " +
                "Values (?, ?, ?)";

        File[] bilder = {   new File("src/main/resources/background_image/hgBild_1.jpg"),
                            new File("src/main/resources/background_image/hgBild_2.jpg"),
                            new File("src/main/resources/background_image/hgBild_3.jpg")};


        try (PreparedStatement stmt = DatabaseUtils.getConnection(path).prepareStatement(query)) {
            for (int i=0; i<=2; i++) {
                stmt.setString(1, "Bild");
                stmt.setString(2, bilder[i].getName());
                stmt.setBytes(3, BlobHandling.fileToBytes(bilder[i]));
                stmt.execute();
                DatabaseUtils.commit();
            }

        } catch (SQLException e) {
            DatabaseUtils.rollback(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void generateHintergrundFarbe(String path) throws SQLException {

        String query_farbe = "INSERT INTO Hintergrund(Typ, Farbe) " +
                "Values (?, ?)";

        Color[] farben = {Color.RED, Color.GREEN, Color.BLUE};

        try (PreparedStatement stmt = DatabaseUtils.getConnection(path).prepareStatement(query_farbe)) {
            for (int i=0; i<=2; i++) {
                stmt.setString(1, "Farbe");
                stmt.setInt(2, farben[i].getRGB());
                stmt.execute();
                DatabaseUtils.commit();
            }

        } catch (SQLException e) {
            DatabaseUtils.rollback(e);
        }

    }
    public static void generateHintergrund(String path) throws SQLException {
        generateHintergrundFarbe(path);
        generateHintergrundBild(path);
    }
}
