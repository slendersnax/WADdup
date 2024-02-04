package org.slendersnax.waddup.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GZDoomLauncher {
    private final String osname;
    private final PropWrapper settingsHandler;

    public GZDoomLauncher(String _osname) {
        osname = _osname;
        settingsHandler = new PropWrapper();
    }

    public void run(String iwadPath, ArrayList<WADComponent> pwadList) {
        boolean usePortable, useWine, useWinePrefix, useGamemode;

        usePortable = settingsHandler.getProperty(PropWrapper.FILE_SETTINGS_INDEX, SlenderConstants.SETTINGS_NIX_USE_PORTABLE).equals("True");
        useWine = settingsHandler.getProperty(PropWrapper.FILE_SETTINGS_INDEX, SlenderConstants.SETTINGS_NIX_USE_WINE).equals("True");
        useWinePrefix = settingsHandler.getProperty(PropWrapper.FILE_SETTINGS_INDEX, SlenderConstants.SETTINGS_NIX_USE_WINE_PREFIX).equals("True");
        useGamemode = settingsHandler.getProperty(PropWrapper.FILE_SETTINGS_INDEX, SlenderConstants.SETTINGS_NIX_USE_GAMEMODE).equals("True");

        if (osname.equals("Linux")) {
            if (!useWine) {
                runLinuxNative(iwadPath, pwadList, usePortable, useGamemode);
            }
            else {
                runLinuxWine(iwadPath, pwadList, useWinePrefix, useGamemode);
            }
        }
        else if (osname.equals("Windows")) {
            runWindows(iwadPath, pwadList);
        }
    }

    public void runLinuxNative(String iwadPath, ArrayList<WADComponent> pwadList, boolean usePortable, boolean useGamemode) {
        List<String> cmdBuilder = new ArrayList<String>();

        if (useGamemode) {
            cmdBuilder.add("gamemoderun");
        }

        if (usePortable) {
            cmdBuilder.add(settingsHandler.getProperty(PropWrapper.FILE_SETTINGS_INDEX, SlenderConstants.SETTINGS_NIX_PORTABLE_EXE));
        }
        else {
            cmdBuilder.add("gzdoom");
        }

        cmdBuilder.add("-iwad");
        cmdBuilder.add(iwadPath);

        if (!pwadList.isEmpty()) {
            for (WADComponent sessionWad : pwadList) {
                if (sessionWad.sFileType.equals("deh")) {
                    cmdBuilder.add("-deh");
                }
                else {
                    cmdBuilder.add("-file");
                }
                cmdBuilder.add(sessionWad.sWADPath);
            }
        }

        ProcessBuilder pb = new ProcessBuilder(cmdBuilder);
        Process process;

        try {
            process = pb.start();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public void runLinuxWine(String iwadPath, ArrayList<WADComponent> pwadList, boolean useWinePrefix, boolean useGamemode) {
        List<String> cmdBuilder = new ArrayList<String>();

        if (useGamemode) {
            cmdBuilder.add("gamemoderun");
        }

        cmdBuilder.add("wine");
        cmdBuilder.add(settingsHandler.getProperty(PropWrapper.FILE_SETTINGS_INDEX, SlenderConstants.SETTINGS_NIX_WIN_EXE));

        cmdBuilder.add("-iwad");
        cmdBuilder.add(iwadPath);

        if (!pwadList.isEmpty()) {
            for (WADComponent sessionWad : pwadList) {
                if (sessionWad.sFileType.equals("deh")) {
                    cmdBuilder.add("-deh");
                }
                else {
                    cmdBuilder.add("-file");
                }
                cmdBuilder.add(sessionWad.sWADPath);
            }
        }

        ProcessBuilder pb = new ProcessBuilder(cmdBuilder);

        if (useWinePrefix) {
            pb.environment().put("WINEPREFIX", settingsHandler.getProperty(PropWrapper.FILE_SETTINGS_INDEX, SlenderConstants.SETTINGS_WINE_PREFIX));
        }

        Process process;

        try {
            process = pb.start();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public void runWindows(String iwadPath, ArrayList<WADComponent> pwadList) {
        List<String> cmdBuilder = new ArrayList<String>();
        cmdBuilder.add(settingsHandler.getProperty(PropWrapper.FILE_SETTINGS_INDEX, SlenderConstants.SETTINGS_WIN_EXE));

        cmdBuilder.add("-iwad");
        cmdBuilder.add(iwadPath);

        if (!pwadList.isEmpty()) {
            for (WADComponent sessionWad : pwadList) {
                if (sessionWad.sFileType.equals("deh")) {
                    cmdBuilder.add("-deh");
                }
                else {
                    cmdBuilder.add("-file");
                }
                cmdBuilder.add(sessionWad.sWADPath);
            }
        }

        ProcessBuilder pb = new ProcessBuilder(cmdBuilder);
        Process process;

        try {
            process = pb.start();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
