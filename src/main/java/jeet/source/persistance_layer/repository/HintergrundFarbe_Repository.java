package jeet.source.persistance_layer.repository;

import jeet.source.logic_layer.entity.persistent_entity.HintergrundFarbe;
import jeet.source.persistance_layer.DatabaseUtils;
import jeet.source.persistance_layer.Repository;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HintergrundFarbe_Repository implements Repository<HintergrundFarbe> {

    @Override
    public List<HintergrundFarbe> getAll() {

        List<HintergrundFarbe> result = new ArrayList<>();
        String query = "SELECT HintergrundID, Farbe " +
                "FROM Hintergrund " +
                "WHERE Typ = ?;";

        try (PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(query)) {

            stmt.setString(1, "Farbe");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    long id = rs.getLong("HintergrundID");
                    int farbe = rs.getInt("Farbe");

                    result.add(new HintergrundFarbe(id, new Color(farbe)));
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
    public HintergrundFarbe getByID(long id_input) {

        HintergrundFarbe result = null;
        String query = "SELECT HintergrundID, Farbe " +
                "FROM Hintergrund " +
                "WHERE HintergrundID = ? AND " +
                    "Typ = ?;";

        try (PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(query)) {

            stmt.setLong(1, id_input);
            stmt.setString(2, "Farbe");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    long id = rs.getLong("HintergrundID");
                    int farbe = rs.getInt("Farbe");

                    result = new HintergrundFarbe(id, new Color(farbe));
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
    public HintergrundFarbe add(HintergrundFarbe entity) {

        HintergrundFarbe result = null;
        String query = "INSERT INTO Hintergrund (Typ, Farbe) " +
                "Values (?, ?)";

        try (PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, "Farbe");
            stmt.setInt(2, entity.getFarbe().getRGB());


            try { // Insert
                stmt.executeUpdate();
            } catch (SQLException e) {
                DatabaseUtils.rollback(e);
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) { // hole Primaerschluessel
                long id = rs.getLong(1);
                result = new HintergrundFarbe(id, entity);
            } catch (SQLException e) {
                DatabaseUtils.rollback(e);
            }

        } catch (Exception e) {
            DatabaseUtils.handleException(e);
        }

        return result;
    }

    @Override
    public void update(HintergrundFarbe entity) {

        String query = "UPDATE Hintergrund " +
                "SET Farbe = ? " +
                "WHERE HintergrundID = ? AND " +
                    "Typ = ?;";
        try {
            try (PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(query)) {

                stmt.setInt(1, entity.getFarbe().getRGB());

                stmt.setLong(2, entity.getID());
                stmt.setString(3, "Farbe");

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

    public void delete(HintergrundFarbe entity) {
        String query = "DELETE FROM Hintergrund " +
                "WHERE HintergrundID = ? AND " +
                    "Typ = ?;";

        try {
            try (PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(query)) {

                stmt.setLong(1, entity.getID());
                stmt.setString(2, "Farbe");

                stmt.executeUpdate();

                DatabaseUtils.commit();

            } catch (SQLException e) {
                DatabaseUtils.rollback(e);
            }
        } catch (Exception e) {
            DatabaseUtils.handleException(e);
        }
    }

}
