package jeet.source.persistance_layer.repository;

import jeet.source.logic_layer.entity.persistent_entity.Lehrveranstaltung;
import jeet.source.logic_layer.entity.persistent_entity.Vorlesung;
import jeet.source.persistance_layer.SetupDatabase;
import jeet.source.persistance_layer.DatabaseUtils;
import jeet.source.persistance_layer.RepoCollection;
import jeet.source.persistance_layer.TestData;
import org.junit.jupiter.api.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class Vorlesung_RepositoryTest {

    @BeforeAll
    static void init() throws SQLException {
    }

    @BeforeEach
    void eachInit() throws SQLException {
        DatabaseUtils.clean();
        SetupDatabase.fullSetup();
        TestData.generateTestData();
        DatabaseUtils.closeConnection();
    }

    @AfterEach
    void eachUninit() throws SQLException {
        DatabaseUtils.closeConnection();
    }

    @AfterAll
    static void Uninit() throws SQLException {
    }

    @Test
    void getAll() {
        List<Vorlesung> java = new ArrayList<>();
        long[] lvid_to_vl   = {1, 1, 1, 4, 4};
        String[] VL_Namen   = {"TheoInf_1", "TheoInf_2", "TheoInf_3", "Statistik_1", "Statistik_2"};
        String[] VL_Autoren = {"Sprengel",  "Sprengel",  "Hovestadt", "Ahlers",      "Heine"};
        String[] VL_Datum   = { LocalDate.of(2021, 12,  1).toString(),
                                LocalDate.of(2021, 12,  8).toString(),
                                LocalDate.of(2021, 12, 15).toString(),
                                LocalDate.of(2022, 3,  14).toString(),
                                LocalDate.of(2022, 3,  21).toString()};
        int[] VL_Nummern    = {1, 2, 3, 1, 2};

        for (int i=0; i<=4L; i++) {
            Lehrveranstaltung lv = RepoCollection.lehrveranstaltung.getByID(lvid_to_vl[i]);
            Vorlesung vl = new Vorlesung(i+1, lv, VL_Namen[i], VL_Autoren[i], LocalDate.parse(VL_Datum[i]), VL_Nummern[i]);
            java.add(vl);
        }

        List<Vorlesung> jdbc = RepoCollection.vorlesung.getAll();

        assertEquals(java, jdbc);
    }

    @Test
    void getByID() {
        Lehrveranstaltung java_lv = new Lehrveranstaltung(4, "Statistik", "Bin2/Mdi2", "SoSe22", "Ahlers", LocalDate.of(2022, 3,  14));
        Vorlesung java = new Vorlesung(4, java_lv, "Statistik_1", "Ahlers", LocalDate.of(2022, 3,  14), 1);

        Vorlesung jdbc = RepoCollection.vorlesung.getByID(4);

        assertEquals(java, jdbc);
    }

    @Test
    void add() {
        Lehrveranstaltung java_lv = RepoCollection.lehrveranstaltung.getByID(3);
        Vorlesung java = new Vorlesung(6, java_lv, "SE_1_1", "Bruns", LocalDate.of(2023, 5,  15), 1);

        List<Vorlesung> jdbc = RepoCollection.vorlesung.getAll();
        RepoCollection.vorlesung.add(java);
        List<Vorlesung> added = RepoCollection.vorlesung.getAll();
        added.removeAll(jdbc);

        assertEquals(java, added.get(0));
    }

    @Test
    void update() {
        List<Vorlesung> jdbc = RepoCollection.vorlesung.getAll();
        Vorlesung changed = RepoCollection.vorlesung.getByID(2);
        changed.setAutor("-----");

        assertFalse(jdbc.contains(changed));
        RepoCollection.vorlesung.update(changed);
        assertTrue(RepoCollection.vorlesung.getAll().contains(changed));
    }

    @Test
    void delete() {
        List<Vorlesung> jdbc = RepoCollection.vorlesung.getAll();
        Vorlesung toDelete = RepoCollection.vorlesung.getByID(2);

        assertTrue(jdbc.contains(toDelete));
        RepoCollection.vorlesung.delete(toDelete);
        assertFalse(RepoCollection.vorlesung.getAll().contains(toDelete));
    }

    @Test
    void getVorlesungenByLehrveranstaltung() {
        Lehrveranstaltung lv = RepoCollection.lehrveranstaltung.getByID(1);
        List<Vorlesung> allAndFilter = RepoCollection.vorlesung.getAll();

        allAndFilter.removeIf(item -> !Objects.equals(item.getLv(), lv));

        List<Vorlesung> selectSpecific = RepoCollection.vorlesung.getVorlesungenByLehrveranstaltung(lv);

        assertEquals(allAndFilter, selectSpecific);
    }
}