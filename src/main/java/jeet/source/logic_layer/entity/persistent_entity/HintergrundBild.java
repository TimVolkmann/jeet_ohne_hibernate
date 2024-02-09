package jeet.source.logic_layer.entity.persistent_entity;

import java.io.File;
import java.util.Objects;

public class HintergrundBild extends Hintergrund {

    private String name;
    private File bild;

    public HintergrundBild(String name, File bild) {
        this.name = name;
        this.bild = bild;
    }

    public HintergrundBild(long id, String name, File bild) {
        this.id = id;
        this.name = name;
        this.bild = bild;
    }

    public HintergrundBild(long id, HintergrundBild entity) {
        this.id = id;
        this.name = entity.getName();
        this.bild = entity.getBild();
    }

    //------------------------------------------------------------------------------------------------------------------


    public String getName() {
        return name;
    }

    public File getBild() {
        return bild;
    }

    //------------------------------------------------------------------------------------------------------------------

    /*
     * Um Files einheitlich behandeln zu können, werden alle in den temporären Zwischenspeicher kopiert.
     */
    public void setName(String name) {
        this.name = name;
        File newFile = new File(this.bild.getParent() + "/" + this.name);
        this.bild.renameTo(newFile);
        this.bild = newFile;
    }

    public void setBild(File bild) {
        this.bild = bild;
    }

    //------------------------------------------------------------------------------------------------------------------


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HintergrundBild:\n");
        sb.append("\tid   =").append(id).append("\n");
        sb.append("\tname =").append(name).append("\n");
        sb.append("\tbild =").append(bild).append("\n");
        return sb.toString();
    }

    // ungenauer Vergleich von Files (nur der Name)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HintergrundBild hintergrundBild)) return false;
        return Objects.equals(getID(), hintergrundBild.getID()) &&
                Objects.equals(getName(), hintergrundBild.getName()) &&
                Objects.equals(getBild().getName(), hintergrundBild.getBild().getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID(), getName(), getBild().getName());
    }
}
