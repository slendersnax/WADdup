package org.slendersnax.waddup.repository;

import org.slendersnax.waddup.exception.RepositoryException;
import org.slendersnax.waddup.infrastructure.AppDirectories;
import org.slendersnax.waddup.model.WADModel;
import org.slendersnax.waddup.model.WADSession;
import org.slendersnax.waddup.model.Config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Comparator;

public class ConfigRepository {
    private final Path configsFile;
    private final String ITEM_SEPARATOR = ";;";

    public ConfigRepository() throws RepositoryException {
        configsFile = AppDirectories.getConfigsFile();

        try {
            Files.createDirectories(
                configsFile.getParent()
            );

            if (Files.notExists(configsFile)) {
                Files.createFile(configsFile);
            }
        }
        catch (IOException ex) {
            throw new RepositoryException("Failed to initialise the configs repository.");
        }
    }

    public List<Config> load() {
        ArrayList<Config> configs = new ArrayList<Config>();

        Properties configsProps = new Properties();

        try (InputStream in = Files.newInputStream(configsFile)) {
            configsProps.loadFromXML(in);

            for (String key : configsProps.stringPropertyNames()) {
                String[] wadPaths = configsProps.getProperty(key).split(ITEM_SEPARATOR);

                WADModel iWad;
                ArrayList<WADModel> pWads = new ArrayList<>();

                iWad = new WADModel(wadPaths[0]);

                for (int i = 1; i < wadPaths.length; i ++) {
                    pWads.add(new WADModel(wadPaths[i]));
                }

                configs.add(new Config(key, new WADSession(iWad, pWads)));
            }

            configs.sort(Comparator.comparing(Config::getName, String.CASE_INSENSITIVE_ORDER));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return configs;
    }

    public void save(List<Config> configs) {
        Properties configsProps = new Properties();

        try (OutputStream out = Files.newOutputStream(configsFile)) {
            for (Config config : configs) {
                ArrayList<String> wadPaths = new ArrayList<String>();

                for (WADModel wad : config.getWadSession().getpWADs()) {
                    wadPaths.add(wad.getWADPath());
                }

                wadPaths.add(0, config.getWadSession().getiWAD().getWADPath());
                String joinedWadPaths = String.join(ITEM_SEPARATOR, wadPaths);

                configsProps.setProperty(config.getName(), joinedWadPaths);
            }

            configsProps.storeToXML(out, null);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
