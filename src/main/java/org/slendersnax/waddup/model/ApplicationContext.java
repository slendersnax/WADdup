package org.slendersnax.waddup.model;

import org.slendersnax.waddup.exception.RepositoryException;
import org.slendersnax.waddup.infrastructure.PropWrapper;
import org.slendersnax.waddup.repository.SettingsRepository;

public class ApplicationContext {
    private final Settings settings;
    private final SettingsRepository settingsRepository;
    private final PropWrapper propWrapper;
    private final WADSession wadSession;

    public ApplicationContext() throws RepositoryException {
        settingsRepository = new SettingsRepository();
        settings = settingsRepository.load();

        propWrapper = new PropWrapper();
        wadSession = new WADSession();
    }

    public Settings getSettings() {
        return settings;
    }

    public SettingsRepository getSettingsRepository() {
        return settingsRepository;
    }

    public PropWrapper getPropWrapper() {
        return propWrapper;
    }

    public WADSession getWadSession() {
        return wadSession;
    }
}
