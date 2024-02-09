package jeet.source.logic_layer.control;

import jeet.source.logic_layer.entity.persistent_entity.Lehrveranstaltung;
import jeet.source.logic_layer.entity.persistent_entity.Vorlesung;
import jeet.source.persistance_layer.DatabaseUtils;
import jeet.source.persistance_layer.RepoCollection;
import jeet.source.persistance_layer.SetupDatabase;
import jeet.source.persistance_layer.TestData;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class C_LehrinhaltTest {

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

    //------------------------------------------------------------------------------------------------------------------

    @Test
    void getAllLehrveranstaltung() {
        C_Lehrinhalt control = new C_Lehrinhalt();

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

        List<Lehrveranstaltung> jdbc = control.getAllLehrveranstaltung();

        assertEquals(java, jdbc);
    }

    @Test
    void getLehrveranstaltungByID() {
        C_Lehrinhalt control = new C_Lehrinhalt();

        Lehrveranstaltung java = new Lehrveranstaltung(4, "Statistik", "Bin2/Mdi2", "SoSe22", "Ahlers", LocalDate.of(2022, 3,  14));
        Lehrveranstaltung jdbc = control.getLehrveranstaltungByID(4);

        assertEquals(java, jdbc);

    }

    @Test
    void persistLehrveranstaltung() {
        C_Lehrinhalt control = new C_Lehrinhalt();

        Lehrveranstaltung java = new Lehrveranstaltung(7, "BWL", "Bin3/Mdi1", "WiSe21/22", "Koock", LocalDate.of(2022, 10,  10));
        List<Lehrveranstaltung> jdbc = control.getAllLehrveranstaltung();
        Lehrveranstaltung update = control.persistLehrveranstaltung(java);
        List<Lehrveranstaltung> added = control.getAllLehrveranstaltung();
        added.removeAll(jdbc);

        assertEquals(java, added.get(0));
        assertEquals(java, update);
    }

    @Test
    void updateLehrveranstaltung() {
        C_Lehrinhalt control = new C_Lehrinhalt();

        List<Lehrveranstaltung> jdbc = control.getAllLehrveranstaltung();
        Lehrveranstaltung changed = control.getLehrveranstaltungByID(2);
        changed.setAutor("-----");

        assertFalse(jdbc.contains(changed));
        control.updateLehrveranstaltung(changed);
        assertTrue(control.getAllLehrveranstaltung().contains(changed));
    }

    @Test
    void deleteLehrveranstaltung() {
        C_Lehrinhalt control = new C_Lehrinhalt();

        List<Lehrveranstaltung> jdbc = control.getAllLehrveranstaltung();
        Lehrveranstaltung toDelete = control.getLehrveranstaltungByID(2);

        assertTrue(jdbc.contains(toDelete));
        control.deleteLehrveranstaltung(toDelete);
        assertFalse(control.getAllLehrveranstaltung().contains(toDelete));
    }

    //------------------------------------------------------------------------------------------------------------------

    @Test
    void getAllVorlesung() {
        C_Lehrinhalt control = new C_Lehrinhalt();

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
            Lehrveranstaltung lv = control.getLehrveranstaltungByID(lvid_to_vl[i]);
            Vorlesung vl = new Vorlesung(i+1, lv, VL_Namen[i], VL_Autoren[i], LocalDate.parse(VL_Datum[i]), VL_Nummern[i]);
            java.add(vl);
        }

        List<Vorlesung> jdbc = control.getAllVorlesung();

        assertEquals(java, jdbc);
    }

    @Test
    void getVorlesungByID() {
        C_Lehrinhalt control = new C_Lehrinhalt();

        Lehrveranstaltung java_lv = new Lehrveranstaltung(4, "Statistik", "Bin2/Mdi2", "SoSe22", "Ahlers", LocalDate.of(2022, 3,  14));
        Vorlesung java = new Vorlesung(4, java_lv, "Statistik_1", "Ahlers", LocalDate.of(2022, 3,  14), 1);

        Vorlesung jdbc = control.getVorlesungByID(4);

        assertEquals(java, jdbc);
    }


    @Test
    void persistVorlesung() {
        C_Lehrinhalt control = new C_Lehrinhalt();

        Lehrveranstaltung java_lv = control.getLehrveranstaltungByID(3);
        Vorlesung java = new Vorlesung(6, java_lv, "SE_1_1", "Bruns", LocalDate.of(2023, 5,  15), 1);

        List<Vorlesung> jdbc = control.getAllVorlesung();
        Vorlesung update = control.persistVorlesung(java);
        List<Vorlesung> added = control.getAllVorlesung();
        added.removeAll(jdbc);

        assertEquals(java, added.get(0));
        assertEquals(java, update);
    }

    @Test
    void updateVorlesung() {
        C_Lehrinhalt control = new C_Lehrinhalt();

        List<Vorlesung> jdbc = control.getAllVorlesung();
        Vorlesung changed = control.getVorlesungByID(2);
        changed.setAutor("-----");

        assertFalse(jdbc.contains(changed));
        control.updateVorlesung(changed);
        assertTrue(control.getAllVorlesung().contains(changed));
    }

    @Test
    void deleteVorlesung() {
        C_Lehrinhalt control = new C_Lehrinhalt();

        List<Vorlesung> jdbc = control.getAllVorlesung();
        Vorlesung toDelete = control.getVorlesungByID(2);

        assertTrue(jdbc.contains(toDelete));
        control.deleteVorlesung(toDelete);
        assertFalse(control.getAllVorlesung().contains(toDelete));
    }

    //------------------------------------------------------------------------------------------------------------------

    @Test
    void getVorlesungenByLehrveranstaltung() {
        C_Lehrinhalt control = new C_Lehrinhalt();

        Lehrveranstaltung lv = control.getLehrveranstaltungByID(1);
        List<Vorlesung> allAndFilter = control.getAllVorlesung();

        allAndFilter.removeIf(item -> !Objects.equals(item.getLv(), lv));

        List<Vorlesung> selectSpecific = control.getVorlesungenByLehrveranstaltung(lv);

        assertEquals(allAndFilter, selectSpecific);
    }
}
