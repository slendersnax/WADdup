package org.slendersnax.waddup.infrastructure;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class AppDirectories {
    private static final String APP_NAME = "waddup";

    private AppDirectories() {}

    public static Path getConfigDirectory() {
        switch (Platform.getOperatingSystem()) {
            case WINDOWS:
                return Paths.get(
                    System.getenv("LOCALAPPDATA"),
                    APP_NAME
                );

            case LINUX:
                String xdg = System.getenv("XDG_CONFIG_HOME");
                Path configDir;

                if (xdg == null || xdg.trim().isEmpty()) {
                    configDir = Paths.get(
                        System.getProperty("user.home"),
                        ".config"
                    );
                }
                else {
                    configDir = Paths.get(xdg);
                }

                return configDir.resolve(APP_NAME);

            case MACOS:
                return Paths.get(
                    System.getProperty("user.home"),
                    "Library",
                    "Application Support",
                    APP_NAME
                );

            default:
                return Paths.get(
                    System.getProperty("user.home"),
                    APP_NAME
                );
        }
    }

    public static Path getSettingsFile() {
        return getConfigDirectory().resolve("settings.xml");
    }

    public static Path getConfigsFile() {
        return getConfigDirectory().resolve("configs.xml");
    }
}
