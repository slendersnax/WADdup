package org.slendersnax.waddup.config;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.Action;
import javax.swing.BorderFactory;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import org.slendersnax.waddup.core.VerticalBtnPanel;

public class OptionsPanel extends JPanel {
    private final ConfigHandler configHandler;
    private final VerticalBtnPanel panCategories;
    private final JPanel innerPanel, panSettings, globalSettings, winSettings, nixSettings;
    private final JCheckBox wineCheck, wineprefixCheck;
    private final JButton btnGlobalSettings, btnWinSettings, btnNixSettings, btnSelectWadDir, btnSelectExecWin, btnSelectExecNix, btnSelectWinePrefix, btnSave;
    private final JFileChooser fileChooser;
    private final JLabel wadDirectory, winExec, nixExec, winePrefix;
    private final CardLayout cl;

    private final String sGlobalCardCode, sWinCardCode, sNixCardCode;
    public JButton btnBack;
    
    public OptionsPanel(JFrame _mainFrame) {
        setSize(640, 420);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        sGlobalCardCode = "GLOBAL";
        sWinCardCode = "WINDOWS";
        sNixCardCode = "LINUX";

        cl = new CardLayout();

        configHandler = new ConfigHandler(_mainFrame);
        fileChooser = new JFileChooser();

        // viewing in details mode by default
        Action details = fileChooser.getActionMap().get("viewTypeDetails");
        details.actionPerformed(null);
        fileChooser.setFileHidingEnabled(false);

        panCategories = new VerticalBtnPanel(new Dimension(110, 420));
        panSettings = new JPanel();
        innerPanel = new JPanel();

        globalSettings = new JPanel();
        winSettings = new JPanel();
        nixSettings = new JPanel();

        btnGlobalSettings = new JButton("Global");
        btnWinSettings = new JButton("Windows");
        btnNixSettings = new JButton("Linux");
        btnBack = new JButton("Back");
        btnSave = new JButton("Save");

        btnBack.setMaximumSize(new Dimension(110, 28));

        // global settings items
        btnSelectWadDir = new JButton("Select WAD Folder");
        wadDirectory = new JLabel("[WAD DIRECTORY]");

        // windows settings items
        btnSelectExecWin = new JButton("Select executable");
        winExec = new JLabel("[GZDoom EXECUTABLE LOCATION]");

        // linux settings items
        wineCheck = new JCheckBox("Run using Wine");
        wineprefixCheck = new JCheckBox("Run using specific Wine Prefix");
        btnSelectExecNix = new JButton("Select executable");
        btnSelectWinePrefix = new JButton("Select Wine Prefix location");
        nixExec = new JLabel("[GZDoom EXECUTABLE LOCATION]");
        winePrefix = new JLabel("[WINE PREFIX]");

        panSettings.setLayout(cl);
        panSettings.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));

        globalSettings.setPreferredSize(new Dimension(500, 500));
        winSettings.setPreferredSize(new Dimension(500, 500));
        nixSettings.setPreferredSize(new Dimension(500, 500));

        globalSettings.setLayout(new BoxLayout(globalSettings, BoxLayout.PAGE_AXIS));
        globalSettings.add(Box.createRigidArea(new Dimension(5, 5)));
        globalSettings.add(btnSelectWadDir);
        globalSettings.add(Box.createRigidArea(new Dimension(0, 5)));
        globalSettings.add(wadDirectory);

        winSettings.setLayout(new BoxLayout(winSettings, BoxLayout.PAGE_AXIS));
        winSettings.add(Box.createRigidArea(new Dimension(5, 5)));
        winSettings.add(btnSelectExecWin);
        winSettings.add(Box.createRigidArea(new Dimension(0, 5)));
        winSettings.add(winExec);

        nixSettings.setLayout(new BoxLayout(nixSettings, BoxLayout.PAGE_AXIS));
        nixSettings.add(Box.createRigidArea(new Dimension(7, 0)));
        nixSettings.add(wineCheck);
        nixSettings.add(btnSelectExecNix);
        nixSettings.add(Box.createRigidArea(new Dimension(0, 5)));
        nixSettings.add(nixExec);
        nixSettings.add(Box.createRigidArea(new Dimension(0, 5)));
        nixSettings.add(wineprefixCheck);
        nixSettings.add(btnSelectWinePrefix);
        nixSettings.add(Box.createRigidArea(new Dimension(0, 5)));
        nixSettings.add(winePrefix);

        panCategories.addElem(btnGlobalSettings);
        panCategories.addElem(btnWinSettings);
        panCategories.addElem(btnNixSettings);
        panCategories.add(Box.createVerticalGlue());
        panCategories.addElem(btnSave);
        panCategories.add(btnBack);

        panSettings.add(globalSettings, sGlobalCardCode);
        panSettings.add(winSettings, sWinCardCode);
        panSettings.add(nixSettings, sNixCardCode);

        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.LINE_AXIS));

        innerPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        innerPanel.add(panCategories);
        innerPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        innerPanel.add(panSettings);
        innerPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        add(Box.createRigidArea(new Dimension(0, 5)));
        add(innerPanel);

        addBtnActions(_mainFrame);
        initSettings();
        setVisible(true);
    }

    private void initSettings() {
        String sWadDir = configHandler.getSetting("wad_directory");
        String sWinExec = configHandler.getSetting("windows_executable");
        String sNixExec = configHandler.getSetting("linux_exe");
        String sNixWinePrefix = configHandler.getSetting("wine_prefix");

        if (!sWadDir.isEmpty()) {
            wadDirectory.setText(sWadDir);
        }

        if (!sWinExec.isEmpty()) {
            winExec.setText(sWinExec);
        }

        if (!sNixExec.isEmpty()) {
            nixExec.setText(sNixExec);
        }

        if (!sNixWinePrefix.isEmpty()) {
            winePrefix.setText(sNixWinePrefix);
        }

        if (configHandler.getSetting("use_wine").equals("True")) {
            wineCheck.setSelected(true);
        }

        if (configHandler.getSetting("use_wineprefix").equals("True")) {
            wineprefixCheck.setSelected(true);
        }
    }

    private void addBtnActions(JFrame _mainFrame) {
        btnGlobalSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cl.show(panSettings, sGlobalCardCode);
            }
        });

        btnWinSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cl.show(panSettings, sWinCardCode);
            }
        });

        btnNixSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cl.show(panSettings, sNixCardCode);
            }
        });

        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sWadDir = wadDirectory.getText();
                String sWinExec = winExec.getText();
                String sNixExec = nixExec.getText();
                String sNixWinePrefix = winePrefix.getText();
                boolean bUseWine = wineCheck.isSelected();
                boolean bUseWinePrefix = wineprefixCheck.isSelected();

                if (!sWadDir.isEmpty()) {
                    configHandler.storeSetting("wad_directory", sWadDir);
                }
                if (!sWinExec.isEmpty()) {
                    configHandler.storeSetting("windows_executable", sWinExec);
                }
                if (!sNixExec.isEmpty()) {
                    configHandler.storeSetting("linux_exe", sNixExec);
                }
                if (!sNixWinePrefix.isEmpty()) {
                    configHandler.storeSetting("wine_prefix", sNixWinePrefix);
                }

                configHandler.storeSetting("use_wine", bUseWine ? "True" : "False");
                configHandler.storeSetting("use_wineprefix", bUseWinePrefix ? "True" : "False");
            }
        });

        btnSelectWadDir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int returnVal = fileChooser.showOpenDialog(_mainFrame);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    wadDirectory.setText(file.getAbsolutePath());
                }
            }
        });

        btnSelectExecWin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int returnVal = fileChooser.showOpenDialog(_mainFrame);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    winExec.setText(file.getAbsolutePath());
                }
            }
        });

        btnSelectExecNix.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int returnVal = fileChooser.showOpenDialog(_mainFrame);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    nixExec.setText(file.getAbsolutePath());
                }
            }
        });

        btnSelectWinePrefix.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int returnVal = fileChooser.showOpenDialog(_mainFrame);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    winePrefix.setText(file.getAbsolutePath());
                }
            }
        });
    }
}