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

    // the separator used to join and then split the individual file paths
    // for each config / property in the "config.xml" file
    public static final String CONFIG_ITEM_SEPARATOR        = ";;";
}
