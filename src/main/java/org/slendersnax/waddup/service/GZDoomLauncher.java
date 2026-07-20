package org.slendersnax.waddup.service;

import org.slendersnax.waddup.infrastructure.Platform;
import org.slendersnax.waddup.model.Settings;
import org.slendersnax.waddup.model.WADModel;
import org.slendersnax.waddup.model.WADSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GZDoomLauncher {
    private final Settings settings;

    public GZDoomLauncher(Settings settings) {
        this.settings = settings;
    }

    public void run(WADSession session) {
        switch (Platform.getOperatingSystem()) {
            case WINDOWS:
                runWindows(session.getiWAD().sWADPath, session.getpWADs());
                break;

            case LINUX:
                if (!settings.isUseWine()) {
                    runLinuxNative(session.getiWAD().sWADPath, session.getpWADs(), settings.isUsePortable(), settings.isUseGamemode());
                }
                else {
                    runLinuxWine(session.getiWAD().sWADPath, session.getpWADs(), settings.isUseWinePrefix(), settings.isUseGamemode());
                }
                break;

            case MACOS:
                break;

            default:
                break;
        }
    }

    public void runLinuxNative(String iwadPath, ArrayList<WADModel> pwadList, boolean usePortable, boolean useGamemode) {
        List<String> cmdBuilder = new ArrayList<String>();

        if (useGamemode) {
            cmdBuilder.add("gamemoderun");
        }

        if (usePortable) {
            cmdBuilder.add(settings.getPortableExecutable());
        }
        else {
            cmdBuilder.add("gzdoom");
        }

        cmdBuilder.add("-iwad");
        cmdBuilder.add(iwadPath);

        if (!pwadList.isEmpty()) {
            for (WADModel sessionWad : pwadList) {
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

    public void runLinuxWine(String iwadPath, ArrayList<WADModel> pwadList, boolean useWinePrefix, boolean useGamemode) {
        List<String> cmdBuilder = new ArrayList<String>();

        if (useGamemode) {
            cmdBuilder.add("gamemoderun");
        }

        cmdBuilder.add("wine");
        cmdBuilder.add(settings.getWineExecutable());

        cmdBuilder.add("-iwad");
        cmdBuilder.add(iwadPath);

        if (!pwadList.isEmpty()) {
            for (WADModel sessionWad : pwadList) {
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
            pb.environment().put("WINEPREFIX", settings.getWinePrefix());
        }

        Process process;

        try {
            process = pb.start();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public void runWindows(String iwadPath, ArrayList<WADModel> pwadList) {
        List<String> cmdBuilder = new ArrayList<String>();
        cmdBuilder.add(settings.getWindowsExecutable());

        cmdBuilder.add("-iwad");
        cmdBuilder.add(iwadPath);

        if (!pwadList.isEmpty()) {
            for (WADModel sessionWad : pwadList) {
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
