package jeet.source.persistance_layer.repository;

import jeet.source.logic_layer.entity.persistent_entity.HintergrundFarbe;
import jeet.source.persistance_layer.DatabaseUtils;
import jeet.source.persistance_layer.RepoCollection;
import jeet.source.persistance_layer.SetupDatabase;
import jeet.source.persistance_layer.TestData;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HintergrundFarbe_RepositoryTest {

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
        List<HintergrundFarbe> jdbc = RepoCollection.hintergrundFarbe.getAll();

        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE};

        List<HintergrundFarbe> java = new ArrayList<>();

        for (int i=0; i<=2; i++) {
            java.add(new HintergrundFarbe(i+1, colors[i]));
        }

        assertEquals(jdbc, java);
    }

    @Test
    void getByID() {
        HintergrundFarbe java = new HintergrundFarbe(3, Color.BLUE);
        HintergrundFarbe jdbc = RepoCollection.hintergrundFarbe.getByID(3);

        assertEquals(java, jdbc);
    }

    @Test
    void add() {
        HintergrundFarbe java = new HintergrundFarbe(7, Color.CYAN);
        List<HintergrundFarbe> jdbc = RepoCollection.hintergrundFarbe.getAll();
        RepoCollection.hintergrundFarbe.add(java);
        List<HintergrundFarbe> added = RepoCollection.hintergrundFarbe.getAll();
        added.removeAll(jdbc);

        assertEquals(java, added.get(0));
    }

    @Test
    void update() {
        HintergrundFarbe changed = RepoCollection.hintergrundFarbe.getByID(2);
        changed.setFarbe(Color.MAGENTA);

        assertFalse(RepoCollection.hintergrundFarbe.getAll().contains(changed));
        RepoCollection.hintergrundFarbe.update(changed);
        assertTrue(RepoCollection.hintergrundFarbe.getAll().contains(changed));
    }

    @Test
    void delete() {
        List<HintergrundFarbe> jdbc = RepoCollection.hintergrundFarbe.getAll();
        HintergrundFarbe toDelete = RepoCollection.hintergrundFarbe.getByID(3);

        assertTrue(jdbc.contains(toDelete));

        RepoCollection.hintergrundFarbe.delete(toDelete);
        assertFalse(RepoCollection.hintergrundFarbe.getAll().contains(toDelete));
    }


}