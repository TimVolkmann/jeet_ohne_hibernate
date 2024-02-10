package jeet.source.logic_layer.entity.persistent_entity;

import jeet.source.logic_layer.entity.pattern_helper.HintergrundUpdateVisitor;
import jeet.source.logic_layer.entity.pattern_helper.HintergrundAddVisitor;

import java.awt.Color;
import java.util.Objects;

public class HintergrundFarbe extends Hintergrund {

    private Color farbe;

    public HintergrundFarbe(Color farbe) {
        this.farbe = farbe;
    }

    public HintergrundFarbe(long id, Color farbe) {
        this.id = id;
        this.farbe = farbe;
    }

    public HintergrundFarbe(long id, HintergrundFarbe entity) {
        this.id = id;
        this.farbe = entity.getFarbe();
    }

    //------------------------------------------------------------------------------------------------------------------


    public Color getFarbe() {
        return farbe;
    }

    //------------------------------------------------------------------------------------------------------------------


    public void setFarbe(Color farbe) {
        this.farbe = farbe;
    }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public Hintergrund acceptAdd(HintergrundAddVisitor visitor) {
        return visitor.visitAdd(this);
    }

    @Override
    public void acceptUpdate(HintergrundUpdateVisitor visitor) {
        visitor.visitUpdate(this);
    }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HintergrundFarbe:\n");
        sb.append("\tid    =").append(id).append("\n");
        sb.append("\tfarbe =").append(farbe).append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HintergrundFarbe that)) return false;
        return Objects.equals(getID(), that.getID()) &&
                Objects.equals(getFarbe(), that.getFarbe());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID(), getFarbe());
    }
}
