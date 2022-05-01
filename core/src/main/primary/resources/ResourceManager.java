package main.primary.resources;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URI;
import java.nio.file.Paths;
import java.nio.file.Files;
import main.primary.gameplay.Save;

public class ResourceManager {
    public static void save(Serializable data, String fileName) throws Exception {
        String directoryName = System.getProperty("user.home") + File.separator + "Space-trader-saves" + File.separator + "saves";
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File[] saves = directory.listFiles();
        if (Save.getSelectedSave() >= saves.length) {
            try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(directory + "/" + fileName)))) {
                System.out.println(Paths.get(fileName));
                oos.writeObject(data);
            }
        } else {
            File file = new File(directory + "/" + saves[Save.getSelectedSave()].getName());
            File rename = new File(directory + "/" + fileName);
            file.renameTo(rename);
            try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(directory + "/" + fileName)))) {
                oos.writeObject(data);
            }
        }
    }

    public static Object load(String fileName) throws Exception {
        String directoryName = System.getProperty("user.home") + File.separator + "Space-trader-saves" + File.separator + "saves";
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(directory + "/" + fileName)))) {
            return ois.readObject();
        }
    }
}
