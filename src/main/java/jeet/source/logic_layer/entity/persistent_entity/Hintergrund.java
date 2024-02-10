package jeet.source.logic_layer.entity.persistent_entity;

import jeet.source.logic_layer.entity.pattern_helper.HintergrundUpdateVisitor;
import jeet.source.logic_layer.entity.PersistentEntity;
import jeet.source.logic_layer.entity.pattern_helper.HintergrundAddVisitor;

public abstract class Hintergrund extends PersistentEntity {
    public abstract Hintergrund acceptAdd(HintergrundAddVisitor visitor);
    public abstract void acceptUpdate(HintergrundUpdateVisitor visitor);
}
