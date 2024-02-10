package jeet.source.persistance_layer.repository;

import jeet.source.logic_layer.entity.persistent_entity.Hintergrund;
import jeet.source.logic_layer.entity.persistent_entity.HintergrundBild;
import jeet.source.logic_layer.entity.persistent_entity.HintergrundFarbe;
import jeet.source.persistance_layer.DatabaseUtils;
import jeet.source.persistance_layer.RepoCollection;
import jeet.source.persistance_layer.SetupDatabase;
import jeet.source.persistance_layer.TestData;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Hintergrund_RepositoryTest {

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
        List<Hintergrund>      all    = RepoCollection.hintergrund.getAll();
        List<HintergrundBild>  bilder = RepoCollection.hintergrundBild.getAll();
        List<HintergrundFarbe> farben = RepoCollection.hintergrundFarbe.getAll();

        assertTrue(all.containsAll(bilder));
        assertTrue(all.containsAll(farben));

        all.removeAll(bilder);
        all.removeAll(farben);

        assertTrue(all.isEmpty());
    }

    @Test
    void getByID() {
        assertNull(RepoCollection.hintergrund.getByID(0));

        File file = new File("src/main/resources/blob_handling_temp/hgBild_1.jpg");
        Hintergrund bild = new HintergrundBild(4, file.getName(), file);
        assertEquals(bild, RepoCollection.hintergrund.getByID(4));

        Hintergrund farbe = new HintergrundFarbe(1, Color.RED);
        assertEquals(farbe, RepoCollection.hintergrund.getByID(1));
    }

    @Test
    void add() {
        var few = RepoCollection.hintergrund.getAll();
        File file = new File("src/main/resources/blob_handling_temp/hgBild_4.jpg");
        Hintergrund bild = new HintergrundBild(7, file.getName(), file);
        Hintergrund farbe = new HintergrundFarbe(8, Color.CYAN);

        RepoCollection.hintergrund.add(bild);
        RepoCollection.hintergrund.add(farbe);

        var all = RepoCollection.hintergrund.getAll();
        all.removeAll(few);

        assertTrue(all.remove(bild));
        assertTrue(all.remove(farbe));
        assertTrue(all.isEmpty());
    }

    @Test
    void update() {
        var all = RepoCollection.hintergrund.getAll();

        HintergrundFarbe farbe = (HintergrundFarbe) RepoCollection.hintergrund.getByID(2);
        HintergrundBild  bild  = (HintergrundBild)  RepoCollection.hintergrund.getByID(5);

        bild.setName("neu");
        farbe.setFarbe(Color.BLACK);

        RepoCollection.hintergrund.update(bild);
        RepoCollection.hintergrund.update(farbe);

        var neu = RepoCollection.hintergrund.getAll();

        neu.removeAll(all);

        assertTrue(neu.remove(farbe));
        assertTrue(neu.remove(bild));
        assertTrue(neu.isEmpty());
    }

    @Test
    void delete() {
        var before = RepoCollection.hintergrund.getAll();

        File file = new File("src/main/resources/blob_handling_temp/hgBild_4.jpg");
        Hintergrund bild = new HintergrundBild(7, file.getName(), file);
        Hintergrund farbe = new HintergrundFarbe(8, Color.CYAN);

        RepoCollection.hintergrund.add(bild);
        RepoCollection.hintergrund.add(farbe);

        RepoCollection.hintergrund.delete(bild);
        RepoCollection.hintergrund.delete(farbe);

        var after = RepoCollection.hintergrund.getAll();

        assertEquals(before, after);
    }

}