package jeet.source.persistance_layer.repository;

import jeet.source.logic_layer.entity.pattern_helper.HintergrundUpdateVisitor;
import jeet.source.logic_layer.entity.PersistentEntity;
import jeet.source.logic_layer.entity.persistent_entity.Hintergrund;
import jeet.source.logic_layer.entity.persistent_entity.HintergrundBild;
import jeet.source.logic_layer.entity.persistent_entity.HintergrundFarbe;
import jeet.source.logic_layer.entity.pattern_helper.HintergrundAddVisitor;
import jeet.source.persistance_layer.DatabaseUtils;
import jeet.source.persistance_layer.RepoCollection;
import jeet.source.persistance_layer.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// TODO : Ist das sinnvoll?
public class Hintergrund_Repository implements Repository<Hintergrund>, HintergrundAddVisitor, HintergrundUpdateVisitor {

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
        Hintergrund bild  = RepoCollection.hintergrundBild.getByID(id_input);
        Hintergrund farbe = RepoCollection.hintergrundFarbe.getByID(id_input);

        if (bild  == null && farbe == null) return null;
        if (bild  == null) return farbe;
        if (farbe == null) return bild;

        throw new RuntimeException("Ungültige Werte in Datenbank.");
    }
    // delegieren

    @Override
    public Hintergrund add(Hintergrund entity) {
        return entity.acceptAdd(this);
    }
    // delegieren

    @Override
    public void update(Hintergrund entity) {
        entity.acceptUpdate(this);
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

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public HintergrundBild visitAdd(HintergrundBild bild) {
        //System.out.println("Bild hinzugefügt");
        return RepoCollection.hintergrundBild.add(bild);
    }

    @Override
    public HintergrundFarbe visitAdd(HintergrundFarbe farbe) {
        //System.out.println("Farbe hinzugefügt");
        return RepoCollection.hintergrundFarbe.add(farbe);
    }

    @Override
    public void visitUpdate(HintergrundBild bild) {
        //System.out.println("Bild aktualisiert");
        RepoCollection.hintergrundBild.update(bild);
    }

    @Override
    public void visitUpdate(HintergrundFarbe farbe) {
        //System.out.println("Farbe aktualisiert");
        RepoCollection.hintergrundFarbe.update(farbe);
    }
}
