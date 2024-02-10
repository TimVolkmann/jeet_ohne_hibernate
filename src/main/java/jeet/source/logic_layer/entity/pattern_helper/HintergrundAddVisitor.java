package jeet.source.logic_layer.entity.pattern_helper;

import jeet.source.logic_layer.entity.persistent_entity.HintergrundBild;
import jeet.source.logic_layer.entity.persistent_entity.HintergrundFarbe;

public interface HintergrundAddVisitor {
    HintergrundBild visitAdd(HintergrundBild bild);
    HintergrundFarbe visitAdd(HintergrundFarbe farbe);
}
