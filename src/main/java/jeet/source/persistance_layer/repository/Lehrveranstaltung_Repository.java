package jeet.source.persistance_layer.repository;

import jeet.source.logic_layer.entity.persistent_entity.Lehrveranstaltung;
import jeet.source.persistance_layer.DatabaseUtils;
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
public class Lehrveranstaltung_Repository implements Repository<Lehrveranstaltung> {


    /**
     *
     * @return
     * @author Tim Volkmann
     */
    @Override
    public List<Lehrveranstaltung> getAll() {

        List<Lehrveranstaltung> result = new ArrayList<>();
        String query = "SELECT LehrveranstaltungID, Name, Zielgruppe, Semester, Autor, Datum " +
                "FROM Lehrveranstaltung";

        try (PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    long id = rs.getLong("LehrveranstaltungID");
                    String name = rs.getString("Name");
                    String zielgruppe = rs.getString("Zielgruppe");
                    String semester = rs.getString("Semester");
                    String autor = rs.getString("Autor");
                    String datum = rs.getString("Datum");

                    result.add(new Lehrveranstaltung(id, name, zielgruppe, semester, autor, LocalDate.parse(datum)));
                }
            } catch (SQLException e) {
                DatabaseUtils.rollback(e);
            }
        } catch (Exception e) {
            DatabaseUtils.handleException(e);
        }

        return result;
    }

    /**
     *
     * @param id_input
     * @return
     * @author Tim Volkmann
     */
    @Override
    public Lehrveranstaltung getByID(long id_input) {

        Lehrveranstaltung result = null;
        String query = "SELECT LehrveranstaltungID, Name, Zielgruppe, Semester, Autor, Datum " +
                "FROM Lehrveranstaltung " +
                "WHERE LehrveranstaltungID = ?";

        try (PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(query)) {

            stmt.setLong(1, id_input);

            try (ResultSet rs = stmt.executeQuery()) {

                long id = rs.getLong("LehrveranstaltungID");
                String name = rs.getString("Name");
                String zielgruppe = rs.getString("Zielgruppe");
                String semester = rs.getString("Semester");
                String autor = rs.getString("Autor");
                String datum = rs.getString("Datum");

                result = new Lehrveranstaltung(id, name, zielgruppe, semester, autor, LocalDate.parse(datum));
            } catch (SQLException e) {
                DatabaseUtils.rollback(e);
            }
        } catch (Exception e) {
            DatabaseUtils.handleException(e);
        }

        return result;
    }

    /**
     *
     * @param entity
     * @return
     * @author Tim Volkmann
     */
    @Override
    public Lehrveranstaltung add(Lehrveranstaltung entity) {

        Lehrveranstaltung result = null;
        String query = "INSERT INTO Lehrveranstaltung (Name, Zielgruppe, Semester, Autor, Datum) " +
                "Values (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getZielgruppe());
            stmt.setString(3, entity.getSemester());
            stmt.setString(4, entity.getAutor());
            stmt.setString(5, entity.getDatum().toString());

            try { // Insert
                stmt.executeUpdate();
            } catch (SQLException e) {
                DatabaseUtils.rollback(e);
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) { // hole Primaerschluessel
                long id = rs.getLong(1);
                result = new Lehrveranstaltung(id, entity);
            } catch (SQLException e) {
                DatabaseUtils.rollback(e);
            }

        } catch (Exception e) {
            DatabaseUtils.handleException(e);
        }
        return result;
    }

    /**
     *
     * @param entity
     * @author Tim Volkmann
     */
    @Override
    public void update(Lehrveranstaltung entity) {

        String query = "UPDATE Lehrveranstaltung " +
                "SET Name = ?, " +
                    "Zielgruppe = ?, " +
                    "Semester = ?, " +
                    "Autor = ?, " +
                    "Datum = ? " +
                "WHERE LehrveranstaltungID = ?";
        try {
            try (PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(query)) {

                stmt.setString(1, entity.getName());
                stmt.setString(2, entity.getZielgruppe());
                stmt.setString(3, entity.getSemester());
                stmt.setString(4, entity.getAutor());
                stmt.setString(5, entity.getDatum().toString());

                stmt.setLong(6, entity.getID());

                stmt.executeUpdate();

                DatabaseUtils.commit();

            } catch (SQLException e) {
                DatabaseUtils.rollback(e);
            }
        } catch (Exception e) {
            DatabaseUtils.handleException(e);
        }
    }

    /**
     *
     * @param entity
     * @author Tim Volkmann
     */
    @Override
    public void delete(Lehrveranstaltung entity) {

        String query_vl = "DELETE FROM Vorlesung " +
                "WHERE LehrveranstaltungID = ?";

        String query_lv = "DELETE FROM Lehrveranstaltung " +
                "WHERE LehrveranstaltungID = ?";

        try {
            try (PreparedStatement stmt_vl = DatabaseUtils.getConnection().prepareStatement(query_vl);
                 PreparedStatement stmt_lv = DatabaseUtils.getConnection().prepareStatement(query_lv)) {

                stmt_lv.setLong(1, entity.getID());
                stmt_lv.executeUpdate();

                stmt_vl.setLong(1, entity.getID());
                stmt_vl.executeUpdate();

                DatabaseUtils.commit();

            } catch (SQLException e) {
                DatabaseUtils.rollback(e);
            }
        } catch (Exception e) {
            DatabaseUtils.handleException(e);
        }
    }

}
