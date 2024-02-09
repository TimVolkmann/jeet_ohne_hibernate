package jeet.source.persistance_layer.repository;

import jeet.source.logic_layer.entity.persistent_entity.HintergrundBild;
import jeet.source.persistance_layer.DatabaseUtils;
import jeet.source.persistance_layer.RepoCollection;
import jeet.source.persistance_layer.SetupDatabase;
import jeet.source.persistance_layer.TestData;
import org.junit.jupiter.api.*;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HintergrundBild_RepositoryTest {

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
    void getAll() {
        List<HintergrundBild> jdbc = RepoCollection.hintergrundBild.getAll();

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
    void getByID() {
        HintergrundBild java = new HintergrundBild(4, "hgBild_1.jpg", new File("src/main/resources/blob_handling_temp/hgBild_1.jpg"));
        HintergrundBild jdbc = RepoCollection.hintergrundBild.getByID(4);

        assertEquals(java, jdbc);
    }

    @Test
    void add() {
        HintergrundBild java = new HintergrundBild(7, "hgBild_4.jpg", new File("src/main/resources/blob_handling_temp/hgBild_4.jpg"));
        List<HintergrundBild> jdbc = RepoCollection.hintergrundBild.getAll();
        RepoCollection.hintergrundBild.add(java);
        List<HintergrundBild> added = RepoCollection.hintergrundBild.getAll();
        added.removeAll(jdbc);

        assertEquals(java, added.get(0));
    }

    @Test
    void update() {
        HintergrundBild changed = RepoCollection.hintergrundBild.getByID(5);
        changed.setName("neuerName.jpg");

        assertFalse(RepoCollection.hintergrundBild.getAll().contains(changed));
        RepoCollection.hintergrundBild.update(changed);
        assertTrue(RepoCollection.hintergrundBild.getAll().contains(changed));
    }

    @Test
    void delete() {
        List<HintergrundBild> jdbc = RepoCollection.hintergrundBild.getAll();
        HintergrundBild toDelete = RepoCollection.hintergrundBild.getByID(5);

        assertTrue(jdbc.contains(toDelete));

        RepoCollection.hintergrundBild.delete(toDelete);
        assertFalse(RepoCollection.hintergrundBild.getAll().contains(toDelete));
    }
}