package jeet.source.persistance_layer.repository;

import jeet.source.logic_layer.entity.PersistentEntity;
import jeet.source.logic_layer.entity.persistent_entity.Hintergrund;
import jeet.source.logic_layer.entity.persistent_entity.HintergrundBild;
import jeet.source.logic_layer.entity.persistent_entity.HintergrundFarbe;
import jeet.source.persistance_layer.DatabaseUtils;
import jeet.source.persistance_layer.RepoCollection;
import jeet.source.persistance_layer.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// TODO : Ist das sinnvoll?
public class Hintergrund_Repository implements Repository<Hintergrund> {

    @Override
    public List<Hintergrund> getAll() {
        List<Hintergrund> result = new ArrayList<>();

        List<HintergrundBild> bilder  = RepoCollection.hintergrundBild.getAll();
        List<HintergrundFarbe> farben = RepoCollection.hintergrundFarbe.getAll();

        result.addAll(bilder);
        result.addAll(farben);

        result.sort(Comparator.comparingLong(PersistentEntity::getID));

        return result;
    }

    @Override
    public Hintergrund getByID(long id_input) {
        return null;
    }
    // delegieren

    @Override
    public Hintergrund add(Hintergrund entity) {
        return null;
    }
    // delegieren

    @Override
    public void update(Hintergrund entity) {

    }
    //delegieren

    @Override
    public final void delete(Hintergrund entity) {

        String query = "DELETE FROM Hintergrund " +
                "WHERE HintergrundID = ?;";

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

}
