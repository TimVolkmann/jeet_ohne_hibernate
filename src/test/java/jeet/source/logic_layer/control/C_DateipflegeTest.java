package jeet.source.logic_layer.control;

import jeet.source.logic_layer.entity.persistent_entity.HintergrundBild;
import jeet.source.persistance_layer.DatabaseUtils;
import jeet.source.persistance_layer.SetupDatabase;
import jeet.source.persistance_layer.TestData;
import org.junit.jupiter.api.*;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class C_DateipflegeTest {

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
    void getAllHintergrundBild() {
        C_Dateipflege control = new C_Dateipflege();

        List<HintergrundBild> jdbc = control.getAllHintergrundBild();

        File[] files = {new File("src/main/resources/blob_handling_temp/hgBild_1.jpg"),
                new File("src/main/resources/blob_handling_temp/hgBild_2.jpg"),
                new File("src/main/resources/blob_handling_temp/hgBild_3.jpg")};
        List<HintergrundBild> java = new ArrayList<>();

        for (int i=0; i<=2; i++) {
            java.add(new HintergrundBild(i+4, files[i].getName(), files[i]));
        }

        assertEquals(java, jdbc);
    }

    @Test
    void getHintergrundBildByID() {
        C_Dateipflege control = new C_Dateipflege();

        HintergrundBild java = new HintergrundBild(4, "hgBild_1.jpg", new File("src/main/resources/blob_handling_temp/hgBild_1.jpg"));
        HintergrundBild jdbc = control.getHintergrundBildByID(4);

        assertEquals(java, jdbc);
    }

    @Test
    void persistHintergrundBild() {
        C_Dateipflege control = new C_Dateipflege();

        HintergrundBild java = new HintergrundBild(7, "hgBild_4.jpg", new File("src/main/resources/blob_handling_temp/hgBild_4.jpg"));
        List<HintergrundBild> jdbc = control.getAllHintergrundBild();
        HintergrundBild update = control.persistHintergrundBild(java);
        List<HintergrundBild> added = control.getAllHintergrundBild();
        added.removeAll(jdbc);

        assertEquals(java, added.get(0));
        assertEquals(java, update);
    }

    @Test
    void updateHintergrundBild() {
        C_Dateipflege control = new C_Dateipflege();

        HintergrundBild changed = control.getHintergrundBildByID(5);
        changed.setName("neuerName.jpg");

        assertFalse(control.getAllHintergrundBild().contains(changed));
        control.updateHintergrundBild(changed);
        assertTrue(control.getAllHintergrundBild().contains(changed));
    }

    @Test
    void deleteHintergrundBild() {
        C_Dateipflege control = new C_Dateipflege();

        List<HintergrundBild> jdbc = control.getAllHintergrundBild();
        HintergrundBild toDelete = control.getHintergrundBildByID(5);

        assertTrue(jdbc.contains(toDelete));

        control.deleteHintergrundBild(toDelete);
        assertFalse(control.getAllHintergrundBild().contains(toDelete));
    }
}