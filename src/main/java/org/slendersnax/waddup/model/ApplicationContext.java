package org.slendersnax.waddup.model;

import org.slendersnax.waddup.exception.RepositoryException;
import org.slendersnax.waddup.repository.ConfigRepository;
import org.slendersnax.waddup.repository.SettingsRepository;

public class ApplicationContext {
    private final Settings settings;
    private final SettingsRepository settingsRepository;
    private final ConfigRepository configRepository;
    private final WADSession wadSession;

    public ApplicationContext() throws RepositoryException {
        settingsRepository = new SettingsRepository();
        settings = settingsRepository.load();

        wadSession = new WADSession();
        configRepository = new ConfigRepository();
    }

    public Settings getSettings() {
        return settings;
    }

    public SettingsRepository getSettingsRepository() {
        return settingsRepository;
    }

    public ConfigRepository getConfigRepository() {
        return configRepository;
    }

    public WADSession getWadSession() {
        return wadSession;
    }
}
