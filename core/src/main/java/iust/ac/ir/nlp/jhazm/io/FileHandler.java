package iust.ac.ir.nlp.jhazm.io;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by maJid~ASGARI on 06/01/2015-11:58 AM.
 */
public class FileHandler {

    public static File getFile(String name) throws IOException {
        File file = new File(name);
        if (file.exists()) return file;
        InputStream libraryInputStream = FileHandler.class.getResourceAsStream(name);
        FileUtils.copyInputStreamToFile(libraryInputStream, file);
        return file;
    }

    public static Path getPath(String name) throws IOException {
        Path file = Paths.get(name);
        if (Files.exists(file)) return file;
        InputStream libraryInputStream = FileHandler.class.getResourceAsStream(name);
        FileUtils.copyInputStreamToFile(libraryInputStream, file.toFile());
        return file;
    }
}
