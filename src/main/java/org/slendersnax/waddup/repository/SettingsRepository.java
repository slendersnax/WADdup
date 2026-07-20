package org.slendersnax.waddup.repository;

import org.slendersnax.waddup.exception.RepositoryException;
import org.slendersnax.waddup.model.Settings;
import org.slendersnax.waddup.infrastructure.AppDirectories;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

            settings.setWadDirectory(settingsProps.getProperty(Settings.WAD_DIRECTORY, settings.getWadDirectory()));

            settings.setPreferredWidth(intProperty(settingsProps, Settings.PREF_WIDTH, settings.getPreferredWidth()));
            settings.setPreferredHeight(intProperty(settingsProps, Settings.PREF_HEIGHT, settings.getPreferredHeight()));
            settings.setFontSize(intProperty(settingsProps, Settings.FONT_SIZE, settings.getFontSize()));

            settings.setUsePortable(booleanProperty(settingsProps, Settings.NIX_USE_PORTABLE, settings.isUsePortable()));
            settings.setUseWine(booleanProperty(settingsProps, Settings.NIX_USE_WINE, settings.isUseWine()));
            settings.setUseWinePrefix(booleanProperty(settingsProps, Settings.NIX_USE_WINE_PREFIX, settings.isUseWinePrefix()));
            settings.setUseGamemode(booleanProperty(settingsProps, Settings.NIX_USE_GAMEMODE, settings.isUseGamemode()));

            settings.setPortableExecutable(settingsProps.getProperty(Settings.NIX_PORTABLE_EXE, settings.getPortableExecutable()));
            settings.setWindowsExecutable(settingsProps.getProperty(Settings.WIN_EXE, settings.getWindowsExecutable()));
            settings.setWineExecutable(settingsProps.getProperty(Settings.NIX_WIN_EXE, settings.getWineExecutable()));
            settings.setWinePrefix(settingsProps.getProperty(Settings.WINE_PREFIX, settings.getWinePrefix()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return settings;
    }

    public void save(Settings settings) {
        Properties settingsProps = new Properties();

        try (OutputStream out = Files.newOutputStream(settingsFile)) {
            settingsProps.setProperty(Settings.WAD_DIRECTORY, settings.getWadDirectory());

            settingsProps.setProperty(Settings.PREF_WIDTH, Integer.toString(settings.getPreferredWidth()));
            settingsProps.setProperty(Settings.PREF_HEIGHT, Integer.toString(settings.getPreferredHeight()));
            settingsProps.setProperty(Settings.FONT_SIZE, Integer.toString(settings.getFontSize()));

            settingsProps.setProperty(Settings.NIX_USE_PORTABLE, Boolean.toString(settings.isUsePortable()).toLowerCase());
            settingsProps.setProperty(Settings.NIX_USE_WINE, Boolean.toString(settings.isUseWine()).toLowerCase());
            settingsProps.setProperty(Settings.NIX_USE_WINE_PREFIX, Boolean.toString(settings.isUseWinePrefix()).toLowerCase());
            settingsProps.setProperty(Settings.NIX_USE_GAMEMODE, Boolean.toString(settings.isUseGamemode()).toLowerCase());

            settingsProps.setProperty(Settings.NIX_PORTABLE_EXE, settings.getPortableExecutable());
            settingsProps.setProperty(Settings.WIN_EXE, settings.getWindowsExecutable());
            settingsProps.setProperty(Settings.NIX_WIN_EXE, settings.getWineExecutable());
            settingsProps.setProperty(Settings.WINE_PREFIX, settings.getWinePrefix());

            settingsProps.storeToXML(out, null);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Integer intProperty(Properties props, String key, Integer defaultValue) {
        String value = props.getProperty(key, defaultValue.toString());

        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    private Boolean booleanProperty(Properties props, String key, Boolean defaultValue) {
        String value = props.getProperty(key, defaultValue.toString().toLowerCase());

        return Boolean.parseBoolean(value);
    }
}
