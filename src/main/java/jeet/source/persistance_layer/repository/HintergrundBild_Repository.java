package jeet.source.persistance_layer.repository;

import jeet.source.logic_layer.entity.persistent_entity.HintergrundBild;
import jeet.source.persistance_layer.BlobHandling;
import jeet.source.persistance_layer.DatabaseUtils;
import jeet.source.persistance_layer.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * siehe 'jeet/source/persistance_layer/Repository.java'
 */
public class HintergrundBild_Repository implements Repository<HintergrundBild> {

    @Override
    public List<HintergrundBild> getAll() {
        List<HintergrundBild> result = new ArrayList<>();
        String query = "SELECT HintergrundID, Name, Bild " +
                "FROM Hintergrund " +
                "WHERE Typ = ?;";

        try (PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(query)) {

            stmt.setString(1, "Bild");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    long id = rs.getLong("HintergrundID");
                    String name = rs.getString("Name");
                    byte[] bild = rs.getBytes("Bild");

                    result.add(new HintergrundBild(id, name, BlobHandling.bytesToFile(bild, name)));
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
    public HintergrundBild getByID(long id_input) {
        HintergrundBild result = null;
        String query = "SELECT HintergrundID, Name, Bild " +
                "FROM Hintergrund " +
                "WHERE HintergrundID = ? AND " +
                    "Typ = ?;";

        try (PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(query)) {

            stmt.setLong(1, id_input);
            stmt.setString(2, "Bild");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    long id = rs.getLong("HintergrundID");
                    String name = rs.getString("Name");
                    byte[] bild = rs.getBytes("Bild");

                    result = new HintergrundBild(id, name, BlobHandling.bytesToFile(bild, name));
                }
            } catch (SQLException e) {
                DatabaseUtils.rollback(e);
            }
        } catch (Exception e) {
            DatabaseUtils.handleException(e);
        }

        return result;
    }

    //@Override
    public HintergrundBild add(HintergrundBild entity) {

        HintergrundBild result = null;
        String query = "INSERT INTO Hintergrund (Typ, Name, Bild) " +
                "Values (?, ?, ?)";

        try (PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, "Bild");
            stmt.setString(2, entity.getName());
            stmt.setBytes(3, BlobHandling.fileToBytes(entity.getBild()));


            try { // Insert
                stmt.executeUpdate();
            } catch (SQLException e) {
                DatabaseUtils.rollback(e);
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) { // hole Primaerschluessel
                long id = rs.getLong(1);
                result = new HintergrundBild(id, entity);
            } catch (SQLException e) {
                DatabaseUtils.rollback(e);
            }

        } catch (Exception e) {
            DatabaseUtils.handleException(e);
        }

        return result;
    }

    //@Override
    public void update(HintergrundBild entity) {
        String query = "UPDATE Hintergrund " +
                "SET Name = ?, " +
                    "Bild = ? " +
                "WHERE HintergrundID = ? AND " +
                    "Typ = ?;";
        try {
            try (PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(query)) {

                stmt.setString(1, entity.getName());
                stmt.setBytes(2, BlobHandling.fileToBytes(entity.getBild()));

                stmt.setLong(3, entity.getID());
                stmt.setString(4, "Bild");

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
    public void delete(HintergrundBild entity) {
        String query = "DELETE FROM Hintergrund " +
                "WHERE HintergrundID = ? AND " +
                    "Typ = ?;";

        try {
            try (PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(query)) {

                stmt.setLong(1, entity.getID());
                stmt.setString(2, "Bild");

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
