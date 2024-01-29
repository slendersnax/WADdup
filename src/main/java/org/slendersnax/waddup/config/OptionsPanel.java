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
import org.slendersnax.waddup.core.PropWrapper;

public class OptionsPanel extends JPanel {
    private final PropWrapper settingsHandler;
    private final VerticalBtnPanel panCategories;
    private final JPanel innerPanel, panSettings, globalSettings, winSettings, nixSettings;
    private final JCheckBox wineCheck, wineprefixCheck, portableCheck;
    private final JButton btnGlobalSettings, btnWinSettings, btnNixSettings, btnSelectWadDir, btnSelectExecWin, btnSelectExecNix, btnSelectWinePrefix, btnSelectPortable, btnSave;
    private final JFileChooser fileChooser;
    private final JLabel wadDirectory, winExec, nixExec, winePrefix, portableExecPath;
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

        settingsHandler = new PropWrapper();
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
        portableCheck = new JCheckBox("Run using portable version");
        wineCheck = new JCheckBox("Run using Wine");
        wineprefixCheck = new JCheckBox("Run using specific Wine Prefix");
        btnSelectExecNix = new JButton("Select executable");
        btnSelectWinePrefix = new JButton("Select Wine Prefix location");
        btnSelectPortable = new JButton("Select portable Linux executable");
        nixExec = new JLabel("[GZDoom EXECUTABLE LOCATION]");
        winePrefix = new JLabel("[WINE PREFIX]");
        portableExecPath = new JLabel("[Portable Executable Path]");

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
        nixSettings.add(portableCheck);
        nixSettings.add(btnSelectPortable);
        nixSettings.add(Box.createRigidArea(new Dimension(0, 5)));
        nixSettings.add(portableExecPath);
        nixSettings.add(Box.createRigidArea(new Dimension(0, 5)));
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
        String sWadDir = settingsHandler.getProperty(1,"wad_directory");
        String sWinExec = settingsHandler.getProperty(1,"windows_executable");
        String sNixExec = settingsHandler.getProperty(1,"linux_exe");
        String sNixWinePrefix = settingsHandler.getProperty(1,"wine_prefix");
        String sPortablePath = settingsHandler.getProperty(1,"linux_portable");

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

        if (!sPortablePath.isEmpty()) {
            portableExecPath.setText(sPortablePath);
        }

        portableCheck.setSelected(settingsHandler.getProperty(1, "use_portable").equals("True"));
        wineCheck.setSelected(settingsHandler.getProperty(1, "use_wine").equals("True"));
        wineprefixCheck.setSelected(settingsHandler.getProperty(1, "use_wineprefix").equals("True"));
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
                String sPortablePath = portableExecPath.getText();
                boolean bUsePortable = portableCheck.isSelected();
                boolean bUseWine = wineCheck.isSelected();
                boolean bUseWinePrefix = wineprefixCheck.isSelected();

                if (!sWadDir.isEmpty()) {
                    settingsHandler.storeProperty(1, "wad_directory", sWadDir);
                }
                if (!sWinExec.isEmpty()) {
                    settingsHandler.storeProperty(1, "windows_executable", sWinExec);
                }
                if (!sNixExec.isEmpty()) {
                    settingsHandler.storeProperty(1, "linux_exe", sNixExec);
                }
                if (!sNixWinePrefix.isEmpty()) {
                    settingsHandler.storeProperty(1, "wine_prefix", sNixWinePrefix);
                }
                if (!sPortablePath.isEmpty()) {
                    settingsHandler.storeProperty(1, "linux_portable", sPortablePath);
                }

                settingsHandler.storeProperty(1, "use_portable", bUsePortable ? "True" : "False");
                settingsHandler.storeProperty(1, "use_wine", bUseWine ? "True" : "False");
                settingsHandler.storeProperty(1, "use_wineprefix", bUseWinePrefix ? "True" : "False");
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

        btnSelectPortable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int returnVal = fileChooser.showOpenDialog(_mainFrame);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    portableExecPath.setText(file.getAbsolutePath());
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
