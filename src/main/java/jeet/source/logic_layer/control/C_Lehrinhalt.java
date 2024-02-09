package jeet.source.logic_layer.control;

import jeet.source.logic_layer.entity.persistent_entity.Lehrveranstaltung;
import jeet.source.logic_layer.entity.persistent_entity.Vorlesung;
import jeet.source.persistance_layer.RepoCollection;

import java.util.List;

/*
 * Control-Klasse, die alle Angelegenheiten übernehmen soll, wo es um Lehrveranstaltungen, Vorlesungen oder
 * Präsentationsfolien geht.
 */
public class C_Lehrinhalt {

    public List<Lehrveranstaltung> getAllLehrveranstaltung() {
        return RepoCollection.lehrveranstaltung.getAll();
    }

    public Lehrveranstaltung getLehrveranstaltungByID(long id) {
        return RepoCollection.lehrveranstaltung.getByID(id);
    }

    public Lehrveranstaltung persistLehrveranstaltung(Lehrveranstaltung lv) {
        return RepoCollection.lehrveranstaltung.add(lv);
    }

    public void updateLehrveranstaltung(Lehrveranstaltung lv) {
        RepoCollection.lehrveranstaltung.update(lv);
    }

    public void deleteLehrveranstaltung(Lehrveranstaltung lv) {
        RepoCollection.lehrveranstaltung.delete(lv);
    }

    //------------------------------------------------------------------------------------------------------------------

    public List<Vorlesung> getAllVorlesung() {
        return RepoCollection.vorlesung.getAll();
    }

    public Vorlesung getVorlesungByID(long id) {
        return RepoCollection.vorlesung.getByID(id);
    }

    public Vorlesung persistVorlesung(Vorlesung vl) {
        return RepoCollection.vorlesung.add(vl);
    }

    public void updateVorlesung(Vorlesung vl) {
        RepoCollection.vorlesung.update(vl);
    }

    public void deleteVorlesung(Vorlesung vl) {
        RepoCollection.vorlesung.delete(vl);
    }

    //------------------------------------------------------------------------------------------------------------------

    public List<Vorlesung> getVorlesungenByLehrveranstaltung(Lehrveranstaltung lv) {
        return RepoCollection.vorlesung.getVorlesungenByLehrveranstaltung(lv);
    }

}
