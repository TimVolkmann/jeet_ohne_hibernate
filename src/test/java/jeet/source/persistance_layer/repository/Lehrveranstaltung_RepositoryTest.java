package jeet.source.persistance_layer.repository;

import jeet.source.logic_layer.entity.persistent_entity.Lehrveranstaltung;
import jeet.source.persistance_layer.SetupDatabase;
import jeet.source.persistance_layer.DatabaseUtils;
import jeet.source.persistance_layer.RepoCollection;
import jeet.source.persistance_layer.TestData;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Lehrveranstaltung_RepositoryTest {

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
        List<Lehrveranstaltung> java = new ArrayList<>();
        String[] LV_Namen       = {"TheoInf",   "Prog3",     "SE2",       "Statistik", "DBS2",      "Mathe3"};
        String[] LV_Zielgruppen = {"Bin1",      "Bin3",      "Bin5",      "Bin2/Mdi2", "Bin3",      "Bin3"};
        String[] LV_Semester    = {"WiSe21/22", "WiSe22/23", "WiSe23/24", "SoSe22",    "WiSe22/23", "WiSe22/23"};
        String[] LV_Autoren     = {"Sprengel",  "Peine",     "Bruns",     "Ahlers",    "Heine",     "Pigors"};
        String[] LV_Datum       = { LocalDate.of(2021, 12,  1).toString(),
                                    LocalDate.of(2022, 11, 16).toString(),
                                    LocalDate.of(2023, 10, 31).toString(),
                                    LocalDate.of(2022, 3,  14).toString(),
                                    LocalDate.of(2022, 9,   9).toString(),
                                    LocalDate.of(2022, 12,  1).toString()};


        for (int i=0; i<=5L; i++) {
            java.add(new Lehrveranstaltung(i+1, LV_Namen[i], LV_Zielgruppen[i], LV_Semester[i], LV_Autoren[i],LocalDate.parse(LV_Datum[i])));
        }

        List<Lehrveranstaltung> jdbc = RepoCollection.lehrveranstaltung.getAll();

        assertEquals(java, jdbc);
    }

    @Test
    void getByID() {
        Lehrveranstaltung java = new Lehrveranstaltung(4, "Statistik", "Bin2/Mdi2", "SoSe22", "Ahlers", LocalDate.of(2022, 3,  14));
        Lehrveranstaltung jdbc = RepoCollection.lehrveranstaltung.getByID(4);

        assertEquals(java, jdbc);
    }

    @Test
    void add() {
        Lehrveranstaltung java = new Lehrveranstaltung(7, "BWL", "Bin3/Mdi1", "WiSe21/22", "Koock", LocalDate.of(2022, 10,  10));
        List<Lehrveranstaltung> jdbc = RepoCollection.lehrveranstaltung.getAll();
        RepoCollection.lehrveranstaltung.add(java);
        List<Lehrveranstaltung> added = RepoCollection.lehrveranstaltung.getAll();
        added.removeAll(jdbc);

        assertEquals(java, added.get(0));
    }

    @Test
    void update() {
        List<Lehrveranstaltung> jdbc = RepoCollection.lehrveranstaltung.getAll();
        Lehrveranstaltung changed = RepoCollection.lehrveranstaltung.getByID(2);
        changed.setAutor("-----");

        assertFalse(jdbc.contains(changed));
        RepoCollection.lehrveranstaltung.update(changed);
        assertTrue(RepoCollection.lehrveranstaltung.getAll().contains(changed));
    }

    @Test
    void delete() {
        List<Lehrveranstaltung> jdbc = RepoCollection.lehrveranstaltung.getAll();
        Lehrveranstaltung toDelete = RepoCollection.lehrveranstaltung.getByID(2);

        assertTrue(jdbc.contains(toDelete));
        RepoCollection.lehrveranstaltung.delete(toDelete);
        assertFalse(RepoCollection.lehrveranstaltung.getAll().contains(toDelete));
    }
}