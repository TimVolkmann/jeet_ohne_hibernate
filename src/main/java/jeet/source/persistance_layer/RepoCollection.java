package jeet.source.persistance_layer;

import jeet.source.persistance_layer.repository.Hintergrund_Repository;
import jeet.source.persistance_layer.repository.HintergrundBild_Repository;
import jeet.source.persistance_layer.repository.HintergrundFarbe_Repository;
import jeet.source.persistance_layer.repository.Lehrveranstaltung_Repository;
import jeet.source.persistance_layer.repository.Vorlesung_Repository;

/*
 * Sammlung alles Repositories - gemeinsame Fassade, für Datenbank-Zugriff.
 */
public class RepoCollection {
    public static final Lehrveranstaltung_Repository lehrveranstaltung = new Lehrveranstaltung_Repository();
    public static final Vorlesung_Repository vorlesung = new Vorlesung_Repository();
    public static final Hintergrund_Repository hintergrund = new Hintergrund_Repository();
    public static final HintergrundBild_Repository hintergrundBild = new HintergrundBild_Repository();
    public static final HintergrundFarbe_Repository hintergrundFarbe = new HintergrundFarbe_Repository();
}
