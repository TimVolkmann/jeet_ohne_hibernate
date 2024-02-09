package jeet.source.logic_layer.entity.persistent_entity;

import jeet.source.logic_layer.entity.PersistentEntity;

import java.time.LocalDate;
import java.util.Objects;

public class Lehrveranstaltung extends PersistentEntity {

    private String name;
    private String zielgruppe;
    private String semester;
    private String autor;
    private LocalDate datum;

    public Lehrveranstaltung(String name, String zielgruppe, String semester, String autor, LocalDate datum) {
        this.name = name;
        this.zielgruppe = zielgruppe;
        this.semester = semester;
        this.autor = autor;
        this.datum = datum;
    }

    public Lehrveranstaltung(long id, String name, String zielgruppe, String semester, String autor, LocalDate datum) {
        this.id = id;
        this.name = name;
        this.zielgruppe = zielgruppe;
        this.semester = semester;
        this.autor = autor;
        this.datum = datum;
    }

    public Lehrveranstaltung(long id, Lehrveranstaltung lv) {
        this.id = id;
        this.name = lv.name;
        this.zielgruppe = lv.zielgruppe;
        this.semester = lv.semester;
        this.autor = lv.autor;
        this.datum = lv.datum;
    }


    //------------------------------------------------------------------------------------------------------------------

    public String getName() {
        return name;
    }

    public String getZielgruppe() {
        return zielgruppe;
    }

    public String getSemester() {
        return semester;
    }

    public String getAutor() {
        return autor;
    }

    public LocalDate getDatum() {
        return datum;
    }

    //------------------------------------------------------------------------------------------------------------------

    public void setName(String name) {
        this.name = name;
    }

    public void setZielgruppe(String zielgruppe) {
        this.zielgruppe = zielgruppe;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Lehrveranstaltung:\n");
        sb.append("\tid         = ").append(id).append("\n");
        sb.append("\tname       = ").append(name).append("\n");
        sb.append("\tzielgruppe = ").append(zielgruppe).append("\n");
        sb.append("\tsemester   = ").append(semester).append("\n");
        sb.append("\tautor      = ").append(autor).append("\n");
        sb.append("\tdatum      = ").append(datum).append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lehrveranstaltung that)) return false;
        return Objects.equals(getID(), that.getID()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getZielgruppe(), that.getZielgruppe()) &&
                Objects.equals(getSemester(), that.getSemester()) &&
                Objects.equals(getAutor(), that.getAutor()) &&
                Objects.equals(getDatum(), that.getDatum());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID(), getName(), getZielgruppe(), getSemester(), getAutor(), getDatum());
    }
}
