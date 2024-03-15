package jeet.source.persistance_layer;

import jeet.source.logic_layer.entity.PersistentEntity;

import java.util.List;
/*
 * Gemeinsame generische Schnittstelle, für Datenbank-Zugriff. Jede Entity, die persistiert werden soll, erhält ein
 * Repository parametrisiert durch den eigenen Typ. Nur die konkreten Repos enthalten SQL.
 */
public interface Repository<T extends PersistentEntity> {
    List<T> getAll(); // TODO : weg, nur in manchen repos
    T getByID(long id_input);
    T add(T entity);
    void update(T entity);
    void delete(T entity);
}
