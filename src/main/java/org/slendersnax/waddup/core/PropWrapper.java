package org.slendersnax.waddup.core;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;

public class PropWrapper {
    private final Properties propHandler;
    private File configFile, settingsFile;
    private File[] files;

    public PropWrapper() {
        propHandler = new Properties();
        files = new File[2];
        initConfigFile();
        files[0] = configFile;
        files[1] = settingsFile;
    }

    private void initConfigFile() {
        String osname = System.getProperty("os.name");
        String homedir = System.getProperty("user.home");
        File configdir;

        if (osname.contains("Linux")) {
            configdir = new File(homedir.concat("/.config/waddup"));

            if(!configdir.exists()) {
                configdir.mkdirs();
            }

            configFile = new File(homedir.concat("/.config/waddup/configs.xml"));
            settingsFile = new File(homedir.concat("/.config/waddup/settings.xml"));
        }
        else if (osname.contains("Windows")) {
            configdir = new File(homedir.concat("\\AppData\\Local\\waddup"));

            if(!configdir.exists()) {
                configdir.mkdirs();
            }

            configFile = new File(homedir.concat("\\AppData\\Local\\waddup\\configs.xml"));
            settingsFile = new File(homedir.concat("\\AppData\\Local\\waddup\\settings.xml"));
        }
        else {
            configFile = new File("configs.xml");
            settingsFile = new File("settings.xml");
        }

        try {
            configFile.createNewFile();
            settingsFile.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadConfigsFromXML(File file) {
        try {
            if (file.exists()) {
                propHandler.clear();
                propHandler.loadFromXML(new FileInputStream(file.getAbsolutePath()));
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private void storeConfigsToXML(File file) {
        try {
            if (file.exists()) {
                propHandler.storeToXML(new FileOutputStream(file.getAbsolutePath()), "");
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public void storeProperty(int fileIndex, String propName, String propValue) {
        loadConfigsFromXML(files[fileIndex]);
        propHandler.setProperty(propName, propValue);
        storeConfigsToXML(files[fileIndex]);
    }

    public String getProperty(int fileIndex, String propName) {
        loadConfigsFromXML(files[fileIndex]);

        if (propHandler.containsKey(propName)) {
            return propHandler.getProperty(propName);
        }
        return "";
    }

    public void removeProperty(int fileIndex, String propName) {
        loadConfigsFromXML(files[fileIndex]);
        propHandler.remove(propName);
        storeConfigsToXML(files[fileIndex]);
    }

    public Enumeration<Object> getKeys(int fileIndex) {
        loadConfigsFromXML(files[fileIndex]);
        return propHandler.keys();
    }
}
