package org.slendersnax.waddup.repository;

import org.slendersnax.waddup.exception.RepositoryException;
import org.slendersnax.waddup.model.Settings;
import org.slendersnax.waddup.infrastructure.AppDirectories;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class SettingsRepository {
    private final Path settingsFile;

    public SettingsRepository() throws RepositoryException {
        settingsFile = AppDirectories.getSettingsFile();

        try {
            Files.createDirectories(
                settingsFile.getParent()
            );

            if (Files.notExists(settingsFile)) {
                Files.createFile(settingsFile);
            }
        }
        catch (IOException ex) {
            throw new RepositoryException("Failed to initialise the settings repository.");
        }
    }

    public Settings load() {
        Settings settings = new Settings();
        Properties settingsProps = new Properties();

        try (InputStream in = Files.newInputStream(settingsFile)) {
            settingsProps.loadFromXML(in);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return settings;
    }

    public void save() {

    }
}
