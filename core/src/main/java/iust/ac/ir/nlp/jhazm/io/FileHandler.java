package iust.ac.ir.nlp.jhazm.io;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by maJid~ASGARI on 06/01/2015-11:58 AM.
 */
public class FileHandler {

    public static Path getPath(String name) throws IOException {
        Path file = Paths.get(name);
        if (Files.exists(file)) return file;
        InputStream libraryInputStream = FileHandler.class.getResourceAsStream("/" + name);
        if (libraryInputStream == null)
            libraryInputStream = FileHandler.class.getResourceAsStream("/" + name.substring(name.indexOf("/") + 1));
        if (libraryInputStream == null) return null;
        if (!Files.exists(file.getParent())) Files.createDirectories(file.getParent());
        FileUtils.copyInputStreamToFile(libraryInputStream, file.toFile());
        return file;
    }

    public static boolean prepareFile(String name) {
        Path file = Paths.get(name);
        if (Files.exists(file)) return true;
        InputStream libraryInputStream = FileHandler.class.getResourceAsStream("/" + name);
        if (libraryInputStream == null)
            libraryInputStream = FileHandler.class.getResourceAsStream("/" + name.substring(name.indexOf("/") + 1));
        try {
            if (file.getParent() != null && !Files.exists(file.getParent())) Files.createDirectories(file.getParent());
            FileUtils.copyInputStreamToFile(libraryInputStream, file.toFile());
        } catch (IOException e) {
            Logger.getLogger(FileHandler.class).debug(e);
            return false;
        }
        return true;
    }
}
