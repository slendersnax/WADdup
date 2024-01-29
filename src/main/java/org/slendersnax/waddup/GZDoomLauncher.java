package org.slendersnax.waddup;

import org.slendersnax.waddup.core.WADComponent;
import org.slendersnax.waddup.core.PropWrapper;

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
        boolean usePortable, useWine, useWinePrefix;

        usePortable = settingsHandler.getProperty(1, "use_portable").equals("True");
        useWine = settingsHandler.getProperty(1, "use_wine").equals("True");
        useWinePrefix = settingsHandler.getProperty(1, "use_wineprefix").equals("True");

        if (osname.equals("Linux")) {
            if (!useWine) {
                runLinuxNative(iwadPath, pwadList, usePortable);
            }
            else {
                runLinuxWine(iwadPath, pwadList, useWinePrefix);
            }
        }
        else if (osname.equals("Windows")) {
            runWindows(iwadPath, pwadList);
        }
    }

    public void runLinuxNative(String iwadPath, ArrayList<WADComponent> pwadList, boolean usePortable) {
        List<String> cmdBuilder = new ArrayList<String>();

        if (usePortable) {
            cmdBuilder.add(settingsHandler.getProperty(1, "linux_portable"));
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

    public void runLinuxWine(String iwadPath, ArrayList<WADComponent> pwadList, boolean useWinePrefix) {
        List<String> cmdBuilder = new ArrayList<String>();

        cmdBuilder.add("wine");
        cmdBuilder.add(settingsHandler.getProperty(1, "linux_exe"));

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
            pb.environment().put("WINEPREFIX", settingsHandler.getProperty(1, "wine_prefix"));
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
        cmdBuilder.add(settingsHandler.getProperty(1, "windows_executable"));

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
