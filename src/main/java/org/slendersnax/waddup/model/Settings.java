package org.slendersnax.waddup.model;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class Settings {
    public static final String WAD_DIRECTORY       = "wad_directory";
    public static final String WIN_EXE             = "windows_executable";
    public static final String NIX_WIN_EXE         = "linux_exe";
    public static final String WINE_PREFIX         = "wine_prefix";
    public static final String NIX_PORTABLE_EXE    = "linux_portable";
    public static final String NIX_USE_PORTABLE    = "use_portable";
    public static final String NIX_USE_WINE        = "use_wine";
    public static final String NIX_USE_WINE_PREFIX = "use_wineprefix";
    public static final String NIX_USE_GAMEMODE    = "use_gamemode";
    public static final String PREF_WIDTH          = "preferred_width";
    public static final String PREF_HEIGHT         = "preferred_height";
    public static final String FONT_SIZE           = "font_size";

    // the separator used to join and then split the individual file paths
    // for each config / property in the "config.xml" file
    public static final String CONFIG_ITEM_SEPARATOR        = ";;";

    private String wadDirectory;

    private int preferredWidth;
    private int preferredHeight;

    private int fontSize;

    private boolean usePortable;
    private boolean useWine;
    private boolean useWinePrefix;
    private boolean useGamemode;

    private String portableExecutable;
    private String windowsExecutable;
    private String wineExecutable;
    private String winePrefix;

    public Settings() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        wadDirectory = "";

        // i just like this size
        preferredWidth = gd.getDisplayMode().getWidth() / 2;
        preferredHeight = (int) (gd.getDisplayMode().getHeight() / 1.5);

        fontSize = 12;

        usePortable = false;
        useWine = false;
        useWinePrefix = false;
        useGamemode = false;

        portableExecutable = "";
        windowsExecutable = "";
        wineExecutable = "";
        winePrefix = "";
    }

    public void setWadDirectory(String wadDirectory) {
        this.wadDirectory = wadDirectory;
    }

    public String getWadDirectory() {
        return wadDirectory;
    }

    public int getPreferredWidth() {
        return preferredWidth;
    }

    public void setPreferredWidth(int preferredWidth) {
        this.preferredWidth = preferredWidth;
    }

    public int getPreferredHeight() {
        return preferredHeight;
    }

    public void setPreferredHeight(int preferredHeight) {
        this.preferredHeight = preferredHeight;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public boolean isUsePortable() {
        return usePortable;
    }

    public void setUsePortable(boolean usePortable) {
        this.usePortable = usePortable;
    }

    public boolean isUseWine() {
        return useWine;
    }

    public void setUseWine(boolean useWine) {
        this.useWine = useWine;
    }

    public boolean isUseWinePrefix() {
        return useWinePrefix;
    }

    public void setUseWinePrefix(boolean useWinePrefix) {
        this.useWinePrefix = useWinePrefix;
    }

    public boolean isUseGamemode() {
        return useGamemode;
    }

    public void setUseGamemode(boolean useGamemode) {
        this.useGamemode = useGamemode;
    }

    public String getPortableExecutable() {
        return portableExecutable;
    }

    public void setPortableExecutable(String portableExecutable) {
        this.portableExecutable = portableExecutable;
    }

    public String getWindowsExecutable() {
        return windowsExecutable;
    }

    public void setWindowsExecutable(String windowsExecutable) {
        this.windowsExecutable = windowsExecutable;
    }

    public String getWineExecutable() {
        return wineExecutable;
    }

    public void setWineExecutable(String wineExecutable) {
        this.wineExecutable = wineExecutable;
    }

    public String getWinePrefix() {
        return winePrefix;
    }

    public void setWinePrefix(String winePrefix) {
        this.winePrefix = winePrefix;
    }
}
