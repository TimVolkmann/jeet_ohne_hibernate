package jeet.source.logic_layer.entity;

/*
 * Gemeinsame Schnittstelle für alle Entities, die persistiert werden sollen. Unter anderem nötig zur Parametrisierung
 * von 'jeet/source/persistance_layer/Repository.java'.
 */
public abstract class PersistentEntity {
    protected Long id = null;

    public Long getID() {
        return id;
    }
}
