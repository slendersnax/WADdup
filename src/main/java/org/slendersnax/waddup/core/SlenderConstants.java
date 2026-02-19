package org.slendersnax.waddup.core;

public class SlenderConstants {
    // these are the names of the properties found in the "settings.xml" file
    public static final String SETTINGS_WAD_DIRECTORY       = "wad_directory";
    public static final String SETTINGS_WIN_EXE             = "windows_executable";
    public static final String SETTINGS_NIX_WIN_EXE         = "linux_exe";
    public static final String SETTINGS_WINE_PREFIX         = "wine_prefix";
    public static final String SETTINGS_NIX_PORTABLE_EXE    = "linux_portable";
    public static final String SETTINGS_NIX_USE_PORTABLE    = "use_portable";
    public static final String SETTINGS_NIX_USE_WINE        = "use_wine";
    public static final String SETTINGS_NIX_USE_WINE_PREFIX = "use_wineprefix";
    public static final String SETTINGS_NIX_USE_GAMEMODE    = "use_gamemode";
    public static final String SETTINGS_PREF_WIDTH          = "preferred_width";
    public static final String SETTINGS_PREF_HEIGHT         = "preferred_height";

    // the separator used to join and then split the individual file paths
    // for each config / property in the "config.xml" file
    public static final String CONFIG_ITEM_SEPARATOR        = ";;";

    public static final int STD_BTN_HEIGHT = 28;
}
