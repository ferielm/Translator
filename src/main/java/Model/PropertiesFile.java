package Model;

import Controller.GeneralController;
import javafx.collections.ObservableList;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class PropertiesFile implements TranslationManip{
    public File importTranslations(GeneralController gc) {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setInitialDirectory(new File(getClass().getResource("/properties").getPath()));
        File dir = dirChooser.showDialog(null);
        //fill current file
        File[] files = dir.listFiles();
        HashMap<String, HashMap<Locale, String>> words = new HashMap<>();
        for (File file : files)
            if (file.getName().contains("_")) {
                fillMap(file, words);
            }
        gc.fillTable(words);
        return dir;
    }

    private void fillMap(File file, HashMap<String, HashMap<Locale, String>> words) {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream(file);
            if (input == null) {
                System.out.println("Sorry, unable to find " + file.getName());
                return;
            }

            prop.load(input);

            Enumeration e = prop.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = prop.getProperty(key);
                System.out.println("Key : " + key + ", Value : " + value);
                if (words.containsKey(key)) {
                    System.out.println(key + " exist");
                    words.get(key).put(getLocal(file.getName()), value);
                } else {
                    System.out.println(key + " doesnt exit");
                    HashMap<Locale, String> map = new HashMap<>();
                    map.put(getLocal(file.getName()), value);
                    words.put(key, map);
                }

            }
            System.out.println(words.size());

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void exportTranslations(ObservableList<TranslationRow> items, File dir) throws IOException {
        if (dir == null) {
            String name = JOptionPane.showInputDialog("Insérer le nom de fichier Properties :");
            createPropertiesFile(name, items);

        } else {
            File[] files = dir.listFiles();
            for (File file : files) {
                Locale locale = file.getName().contains("_") ? getLocal(file.getName()) : Locale.FRENCH;
                fillFile(file, items, locale);
            }
        }
    }

    private Locale getLocal(String fileName) {
        String[] tmp = fileName.substring(0, fileName.length() - 11).split("_");
        return Locale.forLanguageTag(tmp[1]);
    }

    private void createPropertiesFile(String fileName, ObservableList<TranslationRow> items) throws IOException {
        File file = null;
        file = new File(getClass().getResource("/properties").getPath(), fileName);
        file.mkdir();

        for (Map.Entry map : items.get(0).getMap().entrySet()) {
            file = new File(getClass().getResource("/properties/" + fileName).getPath(),
                    fileName + "_" + ((Locale) map.getKey()).toLanguageTag() + ".properties");
            file.createNewFile();
            fillFile(file, items, ((Locale) map.getKey()));
            if (((Locale) map.getKey()).toLanguageTag().equals("fr")) {
                File file1 = new File(getClass().getResource("/properties/" + fileName).getPath(),
                        fileName + ".properties");
                FileUtils.copyFile(file, file1);
            }
        }

    }

    private void fillFile(File file, ObservableList<TranslationRow> items, Locale locale) {
        final Properties prop = new Properties();
        OutputStream output = null;

        try {
            output = new FileOutputStream(file);
            // set the properties value
            for (TranslationRow row : items) {
                if (row.getSelect().isSelected()) {
                prop.setProperty(row.getId(), row.getMap().get(locale));}
            }
            // save properties to project root folder
            prop.store(output, null);

        } catch (final IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
