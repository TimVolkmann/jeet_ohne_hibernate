package jeet.source.logic_layer.entity.persistent_entity;

import jeet.source.logic_layer.entity.PersistentEntity;

import java.time.LocalDate;
import java.util.Objects;

public class Vorlesung extends PersistentEntity {

    private Lehrveranstaltung lv;
    private String name;
    private String autor;
    private LocalDate datum;
    private int nummer;
    // pdfFile

    public Vorlesung(Lehrveranstaltung lv, String name, String autor, LocalDate datum, int nummer) {
        this.name = name;
        this.autor = autor;
        this.lv = lv;
        this.datum = datum;
        this.nummer = nummer;
    }

    public Vorlesung(long id, Lehrveranstaltung lv, String name, String autor, LocalDate datum, int nummer) {
        this.id = id;
        this.lv = lv;
        this.name = name;
        this.autor = autor;
        this.datum = datum;
        this.nummer = nummer;
    }

    public Vorlesung(long id, Vorlesung entity) {
        this.id = id;
        this.lv = entity.getLv();
        this.name = entity.getName();
        this.autor = entity.getAutor();
        this.datum = entity.getDatum();
        this.nummer = entity.getNummer();
    }

    //------------------------------------------------------------------------------------------------------------------

    public String getName() {
        return name;
    }

    public String getAutor() {
        return autor;
    }

    public Lehrveranstaltung getLv() {
        return lv;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public int getNummer() {
        return nummer;
    }
    //------------------------------------------------------------------------------------------------------------------

    public void setName(String name) {
        this.name = name;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setLv(Lehrveranstaltung lv) {
        this.lv = lv;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Vorlesung:\n");
        sb.append("\tid     = ").append(id).append("\n");
        sb.append("\tlv:\n"    ).append(lv).append("\n");
        sb.append("\tname   = ").append(name).append("\n");
        sb.append("\tautor  = ").append(autor).append("\n");
        sb.append("\tdatum  = ").append(datum).append("\n");
        sb.append("\tnummer = ").append(nummer).append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vorlesung vorlesung)) return false;
        return Objects.equals(getID(), vorlesung.getID()) &&
                Objects.equals(getLv(), vorlesung.getLv()) &&
                Objects.equals(getName(), vorlesung.getName()) &&
                Objects.equals(getAutor(), vorlesung.getAutor()) &&
                Objects.equals(getDatum(), vorlesung.getDatum()) &&
                Objects.equals(getNummer(), vorlesung.getNummer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID(), getLv(), getName(), getAutor(), getDatum(), getNummer());
    }
}
