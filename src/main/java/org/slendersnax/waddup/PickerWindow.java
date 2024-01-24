package org.slendersnax.waddup;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.BoxLayout;

import java.awt.Container;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slendersnax.waddup.wad_display.WADPanel;
import org.slendersnax.waddup.core.WADComponent;
import org.slendersnax.waddup.config.ConfigHandler;

public class PickerWindow {
    private JFrame mainFrame;
    private JPanel btnContainer, midCardPanel;
    private WADPanel wadContainer;
    private JButton btn_play, btn_saveconfig, btn_loadconfig;
    private CardLayout cl;

    private String basePath, userHome, saveCardCode, loadCardCode, wadCardCode;
    private ConfigHandler configHandler;

    public PickerWindow(String _basePath) {
        mainFrame = new JFrame("WADdup");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(640, 480);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null); // centers it on screen automatically

        basePath = _basePath;
        userHome = System.getProperty("user.home");

        saveCardCode = "SAVE";
        loadCardCode = "LOAD";
        wadCardCode = "WAD";

        configHandler = new ConfigHandler(mainFrame);

        addComponents(mainFrame.getContentPane());
        initBtnActions();

        mainFrame.setVisible(true);
    }

    public void initBtnActions() {
        btn_saveconfig.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!wadContainer.getWadListPanel().getItemList().isEmpty() && !wadContainer.getIwadLabel().getIwadPath().isEmpty()) {
                    cl.show(midCardPanel, saveCardCode);
                    configHandler.setConfigData(wadContainer.getWadListPanel().getItemList(), wadContainer.getIwadLabel().getIwadPath());
                }
            }
        });

        btn_loadconfig.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                configHandler.loadConfig();
                cl.show(midCardPanel, loadCardCode);
            }
        });

        btn_play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<WADComponent> sessionWads = wadContainer.getWadListPanel().getItemList();

                if (!wadContainer.getIwadLabel().getIwadPath().isEmpty()) {
                    List<String> cmdBuilder = new ArrayList<String>();
                    cmdBuilder.add("gzdoom");
                    cmdBuilder.add("-com.slendersnax.waddup.config");
                    cmdBuilder.add(userHome.concat("/.com.slendersnax.waddup.config/gzdoom/gzdoom.ini"));

                    cmdBuilder.add("-iwad");
                    cmdBuilder.add(wadContainer.getIwadLabel().getIwadPath());

                    if (!sessionWads.isEmpty()) {
                        for (WADComponent sessionWad : sessionWads) {
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
                        //exc.printStackTrace();
                    }
                }
                else {
                    wadContainer.getIwadLabel().signalNoIWADError();
                }
            }
        });

        configHandler.btn_cancelSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cl.show(midCardPanel, wadCardCode);
            }
        });

        configHandler.btn_cancelLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cl.show(midCardPanel, wadCardCode);
            }
        });

        configHandler.btn_saveConfig.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    configHandler.saveConfig();
                    cl.show(midCardPanel, wadCardCode);
                } catch (IOException ex) {

                }
            }
        });

        configHandler.btn_loadConfig.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cl.show(midCardPanel, wadCardCode);
                ArrayList<WADComponent> selectedConfigWads = configHandler.getLoadedWads();

                if (!selectedConfigWads.isEmpty()) {
                    wadContainer.getIwadLabel().setIWADprops(selectedConfigWads.get(0).getTitle(), selectedConfigWads.get(0).sWADPath);
                    selectedConfigWads.remove(0);

                    wadContainer.getWadListPanel().setItemList(selectedConfigWads);
                }
            }
        });
    }

    public void addComponents(Container Pane) {
        btnContainer = new JPanel();
        wadContainer = new WADPanel(mainFrame, basePath);
        midCardPanel = new JPanel();
        cl = new CardLayout();

        btn_saveconfig = new JButton("save config");
        btn_loadconfig = new JButton("load config");

        btn_play = new JButton("Play");

        btnContainer.add(btn_saveconfig);
        btnContainer.add(btn_loadconfig);
        btnContainer.add(btn_play);

        mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));

        midCardPanel.setLayout(cl);
        midCardPanel.add(configHandler.savePanel, saveCardCode);
        midCardPanel.add(configHandler.loadPanel, loadCardCode);
        midCardPanel.add(wadContainer, wadCardCode);

        cl.show(midCardPanel, wadCardCode);

        mainFrame.add(Box.createVerticalGlue());
        mainFrame.add(midCardPanel);
        mainFrame.add(btnContainer);
        mainFrame.add(Box.createVerticalGlue());
    }
}