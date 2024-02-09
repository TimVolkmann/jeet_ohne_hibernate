package jeet.source.logic_layer.control;

import jeet.source.logic_layer.entity.persistent_entity.HintergrundBild;
import jeet.source.persistance_layer.RepoCollection;

import java.util.List;

/*
 * Control-Klasse, die alle Angelegenheiten übernehmen soll, wo es darum geht Dateien (Bilder, gif's, Code) anzulegen,
 * abzuspeichern, zu löschen, etc.
 */
public class C_Dateipflege {

    public List<HintergrundBild> getAllHintergrundBild() {
        return RepoCollection.hintergrundBild.getAll();
    }

    public HintergrundBild getHintergrundBildByID(long id) {
        return RepoCollection.hintergrundBild.getByID(id);
    }

    public HintergrundBild persistHintergrundBild(HintergrundBild hg) {
        return RepoCollection.hintergrundBild.add(hg);
    }

    public void updateHintergrundBild(HintergrundBild hg) {
        RepoCollection.hintergrundBild.update(hg);
    }

    public void deleteHintergrundBild(HintergrundBild hg) {
        RepoCollection.hintergrundBild.delete(hg);
    }
}
