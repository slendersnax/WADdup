package org.slendersnax.waddup.ui;

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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import org.slendersnax.waddup.model.Settings;
import org.slendersnax.waddup.repository.SettingsRepository;
import org.slendersnax.waddup.ui.components.VerticalBtnPanel;
import org.slendersnax.waddup.infrastructure.PropWrapper;
import org.slendersnax.waddup.infrastructure.SlenderConstants;
import org.slendersnax.waddup.ui.helpers.NavigationHandler;

public class OptionsPanel extends JPanel implements NavigationHandler {
    private final Settings settings;
    private final SettingsRepository settingsRepository;
    private final VerticalBtnPanel panCategories;
    private final JPanel innerPanel, panSettings, globalSettings, winSettings, nixSettings;
    private final JCheckBox wineCheck, wineprefixCheck, portableCheck, gamemodeCheck;
    private final JButton btnGlobalSettings, btnWinSettings, btnNixSettings, btnSelectWadDir, btnSelectExecWin, btnSelectExecNix, btnSelectWinePrefix, btnSelectPortable, btnSave, btnWadPanel;
    private final JFileChooser fileChooser;
    private final JLabel wadDirectory, winExec, nixExec, winePrefix, portableExecPath;
    private final CardLayout cl;

    private final String sGlobalCardCode, sWinCardCode, sNixCardCode;
    
    public OptionsPanel(Settings settings, SettingsRepository settingsRepository) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.settings = settings;
        this.settingsRepository = settingsRepository;

        Dimension mainFrameSize = new Dimension(settings.getPreferredWidth(), settings.getPreferredHeight());

        sGlobalCardCode = "GLOBAL";
        sWinCardCode = "WINDOWS";
        sNixCardCode = "LINUX";

        cl = new CardLayout();

        fileChooser = new JFileChooser();

        // viewing in details mode by default
        Action details = fileChooser.getActionMap().get("viewTypeDetails");
        details.actionPerformed(null);
        fileChooser.setFileHidingEnabled(false);

        panCategories = new VerticalBtnPanel(new Dimension((int)(mainFrameSize.width * 0.20), mainFrameSize.height));
        panSettings = new JPanel();
        innerPanel = new JPanel();

        globalSettings = new JPanel();
        winSettings = new JPanel();
        nixSettings = new JPanel();

        btnGlobalSettings = new JButton("Global");
        btnWinSettings = new JButton("Windows");
        btnNixSettings = new JButton("Linux");
        btnSave = new JButton("Save");
        btnWadPanel = new JButton("Back to WADs");

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
        gamemodeCheck = new JCheckBox("Use gamemode");

        btnSelectExecNix = new JButton("Select executable");
        btnSelectWinePrefix = new JButton("Select Wine Prefix location");
        btnSelectPortable = new JButton("Select portable Linux executable");
        nixExec = new JLabel("[GZDoom EXECUTABLE LOCATION]");
        winePrefix = new JLabel("[WINE PREFIX]");
        portableExecPath = new JLabel("[Portable Executable Path]");

        panSettings.setLayout(cl);
        panSettings.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));

        globalSettings.setPreferredSize(new Dimension((int)(mainFrameSize.width * 0.80), (int)(mainFrameSize.height * 0.80)));
        winSettings.setPreferredSize(new Dimension((int)(mainFrameSize.width * 0.80), (int)(mainFrameSize.height * 0.80)));
        nixSettings.setPreferredSize(new Dimension((int)(mainFrameSize.width * 0.80), (int)(mainFrameSize.height * 0.80)));

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
        nixSettings.add(gamemodeCheck);
        nixSettings.add(Box.createRigidArea(new Dimension(0, 5)));
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
        panCategories.addLastElem(btnWadPanel);

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
        add(Box.createRigidArea(new Dimension(5, 5)));

        addBtnActions();
        initSettings();
    }

    public void onWadPanelRequested(ActionListener listener) {
        btnWadPanel.addActionListener(listener);
    }

    private void initSettings() {
        wadDirectory.setText(settings.getWadDirectory());
        winExec.setText(settings.getWindowsExecutable());
        nixExec.setText(settings.getWineExecutable());
        winePrefix.setText(settings.getWinePrefix());
        portableExecPath.setText(settings.getPortableExecutable());

        portableCheck.setSelected(settings.isUsePortable());
        wineCheck.setSelected(settings.isUseWine());
        wineprefixCheck.setSelected(settings.isUseWinePrefix());
        gamemodeCheck.setSelected(settings.isUseGamemode());
    }

    private void addBtnActions() {
        // navigation
        btnGlobalSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPanel(sGlobalCardCode);
            }
        });

        btnWinSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPanel(sWinCardCode);
            }
        });

        btnNixSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPanel(sNixCardCode);
            }
        });

        // saving the settings
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                settingsRepository.save(settings);
            }
        });

        // choosing files and directories
        btnSelectWadDir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int returnVal = fileChooser.showOpenDialog(getParent());

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    wadDirectory.setText(file.getAbsolutePath());
                    settings.setWadDirectory(file.getAbsolutePath());
                }
            }
        });

        btnSelectExecWin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int returnVal = fileChooser.showOpenDialog(getParent());

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    winExec.setText(file.getAbsolutePath());
                    settings.setWindowsExecutable(file.getAbsolutePath());
                }
            }
        });

        btnSelectExecNix.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int returnVal = fileChooser.showOpenDialog(getParent());

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    nixExec.setText(file.getAbsolutePath());
                    settings.setWineExecutable(file.getAbsolutePath());
                }
            }
        });

        btnSelectPortable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int returnVal = fileChooser.showOpenDialog(getParent());

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    portableExecPath.setText(file.getAbsolutePath());
                    settings.setPortableExecutable(file.getAbsolutePath());
                }
            }
        });

        btnSelectWinePrefix.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int returnVal = fileChooser.showOpenDialog(getParent());

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    winePrefix.setText(file.getAbsolutePath());
                    settings.setWinePrefix(file.getAbsolutePath());
                }
            }
        });

        // checkbox change listeners
        portableCheck.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                settings.setUsePortable(portableCheck.isSelected());
            }
        });

        wineCheck.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                settings.setUsePortable(wineCheck.isSelected());
            }
        });


        wineprefixCheck.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                settings.setUsePortable(wineprefixCheck.isSelected());
            }
        });


        gamemodeCheck.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                settings.setUsePortable(portableCheck.isSelected());
            }
        });
    }

    @Override
    public void showPanel(String name) {
        switch(name) {
            case "GLOBAL":
                cl.show(panSettings, sGlobalCardCode);
                break;
            case "WINDOWS":
                cl.show(panSettings, sWinCardCode);
                break;
            case "LINUX":
                cl.show(panSettings, sNixCardCode);
                break;
            default:
                break;
        }
    }

    public void showDefaultCard() {
        showPanel(sGlobalCardCode);
    }
}
