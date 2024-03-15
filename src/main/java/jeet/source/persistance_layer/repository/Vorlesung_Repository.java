package jeet.source.persistance_layer.repository;

import jeet.source.logic_layer.entity.persistent_entity.Lehrveranstaltung;
import jeet.source.logic_layer.entity.persistent_entity.Vorlesung;
import jeet.source.persistance_layer.BlobHandling;
import jeet.source.persistance_layer.DatabaseUtils;
import jeet.source.persistance_layer.RepoCollection;
import jeet.source.persistance_layer.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
 * siehe 'jeet/source/persistance_layer/Repository.java'
 */
public class Vorlesung_Repository implements Repository<Vorlesung> {

    @Override
    public List<Vorlesung> getAll() {

        List<Vorlesung> result = new ArrayList<>();
        String query = "SELECT VorlesungID, LehrveranstaltungID, Name, Autor, Datum, Nummer, PDFDatei" +
                "FROM Vorlesung";

        try (PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    long id_vl = rs.getLong("VorlesungID");
                    long id_lv = rs.getLong("LehrveranstaltungID");
                    String name = rs.getString("Name");
                    String autor = rs.getString("Autor");
                    String datum = rs.getString("Datum");
                    int nummer = rs.getInt("Nummer");
                    byte[] pdfDatei = rs.getBytes("PDFDatei");

                    Lehrveranstaltung lv = RepoCollection.lehrveranstaltung.getByID(id_lv);

                    result.add(new Vorlesung(id_vl, lv, name, autor, LocalDate.parse(datum), nummer, BlobHandling.bytesToPDDocument(pdfDatei)));
                }
            } catch (SQLException e) {
                DatabaseUtils.rollback(e);
            }
        } catch (Exception e) {
            DatabaseUtils.handleException(e);
        }

        return result;
    }

    @Override
    public Vorlesung getByID(long id_input) {
        Vorlesung result = null;
        String query = "SELECT VorlesungID, LehrveranstaltungID, Name, Autor, Datum, Nummer, PDFDatei" +
                "FROM Vorlesung " +
                "WHERE VorlesungID = ?";

        try (PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(query)) {

            stmt.setLong(1, id_input);

            try (ResultSet rs = stmt.executeQuery()) {

                long id_vl = rs.getLong("VorlesungID");
                long id_lv = rs.getLong("LehrveranstaltungID");
                String name = rs.getString("Name");
                String autor = rs.getString("Autor");
                String datum = rs.getString("Datum");
                int nummer = rs.getInt("Nummer");
                byte[] pdfDatei = rs.getBytes("PDFDatei");

                Lehrveranstaltung lv = RepoCollection.lehrveranstaltung.getByID(id_lv);

                result = new Vorlesung(id_vl, lv, name, autor, LocalDate.parse(datum), nummer, BlobHandling.bytesToPDDocument(pdfDatei));
            } catch (SQLException e) {
                DatabaseUtils.rollback(e);
            }
        } catch (Exception e) {
            DatabaseUtils.handleException(e);
        }

        return result;
    }

    @Override
    public Vorlesung add(Vorlesung entity) {
        Vorlesung result = null;
        String query = "INSERT INTO Vorlesung (LehrveranstaltungID, Name, Autor, Datum, Nummer, PDFDatei) " +
                "Values (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, entity.getLv().getID());
            stmt.setString(2, entity.getName());
            stmt.setString(3, entity.getAutor());
            stmt.setString(4, entity.getDatum().toString());
            stmt.setInt(5, entity.getNummer());
            stmt.setBytes(6, BlobHandling.pddocumentToBytes(entity.getPdfFile()));

            try { // Insert
                stmt.executeUpdate();
            } catch (SQLException e) {
                DatabaseUtils.rollback(e);
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) { // hole Primaerschluessel
                long id = rs.getLong(1);
                result = new Vorlesung(id, entity);
            } catch (SQLException e) {
                DatabaseUtils.rollback(e);
            }

        } catch (Exception e) {
            DatabaseUtils.handleException(e);
        }
        return result;
    }

    @Override
    public void update(Vorlesung entity) {

        String query = "UPDATE Vorlesung " +
                "SET LehrveranstaltungID = ?, " +
                    "Name = ?, " +
                    "Autor = ?, " +
                    "Datum = ?, " +
                    "Nummer = ? " +
                    "PDFDatei = ? " +
                "WHERE VorlesungID = ?";

        try {
            try (PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(query)) {

                stmt.setLong(1, entity.getLv().getID());
                stmt.setString(2, entity.getName());
                stmt.setString(3, entity.getAutor());
                stmt.setString(4, entity.getDatum().toString());
                stmt.setInt(5, entity.getNummer());
                stmt.setBytes(6, BlobHandling.pddocumentToBytes(entity.getPdfFile()));

                stmt.setLong(7, entity.getID());

                stmt.executeUpdate();

                DatabaseUtils.commit();

            } catch (SQLException e) {
                DatabaseUtils.rollback(e);
            }
        } catch (Exception e) {
            DatabaseUtils.handleException(e);
        }
    }

    @Override
    public void delete(Vorlesung entity) {

        String query = "DELETE FROM Vorlesung " +
                "WHERE VorlesungID = ?";

        try {
            try (PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(query)) {

                stmt.setLong(1, entity.getID());
                stmt.executeUpdate();

                DatabaseUtils.commit();

            } catch (SQLException e) {
                DatabaseUtils.rollback(e);
            }
        } catch (Exception e) {
            DatabaseUtils.handleException(e);
        }
    }

    // TODO: Kann das hier bleiben? Oder aus anderen 5 und dann in Control filtern?
    public List<Vorlesung> getVorlesungenByLehrveranstaltung(Lehrveranstaltung lv) {

        List<Vorlesung> result = new ArrayList<>();
        String query = "SELECT VorlesungID, Name, Autor, Datum, Nummer " +
                "FROM Vorlesung " +
                "WHERE LehrveranstaltungID = ?;";

        try (PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(query)) {

            stmt.setLong(1, lv.getID());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    long id_vl = rs.getLong("VorlesungID");
                    String name = rs.getString("Name");
                    String autor = rs.getString("Autor");
                    String datum = rs.getString("Datum");
                    int nummer = rs.getInt("Nummer");

                    result.add(new Vorlesung(id_vl, lv, name, autor, LocalDate.parse(datum), nummer));
                }
            } catch (SQLException e) {
                DatabaseUtils.rollback(e);
            }
        } catch (Exception e) {
            DatabaseUtils.handleException(e);
        }

        return result;
    }
}
