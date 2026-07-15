package org.slendersnax.waddup.infrastructure;

public final class Platform {
    public enum OperatingSystem {
        LINUX,
        WINDOWS,
        MACOS,
        OTHER
    }

    private static final OperatingSystem OS = detect();

    private Platform() {}

    public static OperatingSystem getOperatingSystem() {
        return OS;
    }

    private static OperatingSystem detect() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("nux") || os.contains("nix") || os.contains("aix")) {
            return OperatingSystem.LINUX;
        }

        if (os.contains("win")) {
            return OperatingSystem.WINDOWS;
        }

        if (os.contains("mac")) {
            return OperatingSystem.MACOS;
        }

        return OperatingSystem.OTHER;
    }

    public static String getFileSep() {
        return System.getProperty("file.separator");
    }
}
