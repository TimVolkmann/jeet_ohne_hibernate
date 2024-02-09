package jeet.source.persistance_layer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 * Methodensammlung, um BLOBs und File's ineinander zu konvertieren.
 * Alle Dateien, die in File's repräsentiert werden, werden in PATH temporär gespeichert.
 *
 * @author Tim Volkmann
 */
public class BlobHandling {
    private static final String PATH = "src/main/resources/blob_handling_temp/";

    public static byte[] fileToBytes(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] imageData = new byte[(int) file.length()];
            fis.read(imageData);
            return imageData;
        }
    }

    public static File bytesToFile(byte[] bytes, String name) throws IOException {
        File file = new File(PATH + name);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(bytes);
        }
        return file;
    }

}
