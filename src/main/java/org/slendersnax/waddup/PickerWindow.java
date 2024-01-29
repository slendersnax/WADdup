package org.slendersnax.waddup;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Box;
import javax.swing.BoxLayout;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.util.ArrayList;

import org.slendersnax.waddup.config.*;
import org.slendersnax.waddup.wad_display.WADPanel;
import org.slendersnax.waddup.core.WADComponent;

public class PickerWindow {
    private JFrame mainFrame;
    private JPanel btnContainer, midCardPanel;
    private WADPanel wadContainer;
    private final OptionsPanel optionsPanel;
    private JButton btn_play, btn_saveconfig, btn_loadconfig, btn_options;
    private CardLayout cl;

    private String basePath;
    private final String saveCardCode, loadCardCode, wadCardCode, optionsCardCode;
    private final String osname;
    private final ConfigHandler configHandler;
    private final GZDoomLauncher launcher;

    public PickerWindow() {
        mainFrame = new JFrame("WADdup");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(640, 480);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null); // centers it on screen automatically

        configHandler = new ConfigHandler(mainFrame);
        optionsPanel = new OptionsPanel(mainFrame);

        basePath = configHandler.getPropHandler().getProperty(1, "wad_directory");

        String propOsname = System.getProperty("os.name");
        if (propOsname.contains("Linux")) {
            osname = "Linux";
        }
        else if (propOsname.contains("Windows")) {
            osname = "Windows";
        }
        else {
            osname = "unknown";
        }
        launcher = new GZDoomLauncher(osname);

        saveCardCode = "SAVE";
        loadCardCode = "LOAD";
        wadCardCode = "WAD";
        optionsCardCode = "OPTIONS";

        addComponents();
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

        btn_options.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cl.show(midCardPanel, optionsCardCode);
            }
        });

        btn_play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<WADComponent> sessionWads = wadContainer.getWadListPanel().getItemList();

                if (!wadContainer.getIwadLabel().getIwadPath().isEmpty()) {
                    launcher.run(wadContainer.getIwadLabel().getIwadPath(), sessionWads);
                }
                else {
                    JOptionPane.showMessageDialog(mainFrame, "You have no IWAD selected", "Error", JOptionPane.WARNING_MESSAGE);
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
                    ex.printStackTrace();
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

        optionsPanel.btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cl.show(midCardPanel, wadCardCode);
            }
        });
    }

    public void addComponents() {
        btnContainer = new JPanel();
        wadContainer = new WADPanel(mainFrame, basePath);
        midCardPanel = new JPanel();
        cl = new CardLayout();

        btn_saveconfig = new JButton("save WAD config");
        btn_loadconfig = new JButton("load WAD config");

        btn_play = new JButton("Play");
        btn_options = new JButton("Settings");

        btnContainer.add(btn_options, BorderLayout.WEST);
        btnContainer.add(btn_saveconfig, BorderLayout.WEST);
        btnContainer.add(btn_loadconfig, BorderLayout.WEST);
        btnContainer.add(btn_play, BorderLayout.EAST);

        mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));

        midCardPanel.setLayout(cl);
        midCardPanel.add(configHandler.savePanel, saveCardCode);
        midCardPanel.add(configHandler.loadPanel, loadCardCode);
        midCardPanel.add(wadContainer, wadCardCode);
        midCardPanel.add(optionsPanel, optionsCardCode);

        cl.show(midCardPanel, wadCardCode);

        mainFrame.add(Box.createVerticalGlue());
        mainFrame.add(midCardPanel);
        mainFrame.add(btnContainer);
        mainFrame.add(Box.createVerticalGlue());
    }
}