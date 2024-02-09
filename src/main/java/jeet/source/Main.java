package jeet.source;

import jeet.source.persistance_layer.DatabaseUtils;
import jeet.source.persistance_layer.SetupDatabase;
import jeet.source.persistance_layer.TestData;

import java.awt.*;
import java.io.File;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        SetupDatabase.initDatabase();

        int[] farben = {-65536, -16711936, -16776961};

        for(int i : farben) {
            System.out.println(new Color(i));
        }
    }
}
