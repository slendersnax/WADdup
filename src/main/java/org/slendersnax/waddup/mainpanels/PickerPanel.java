package org.slendersnax.waddup.mainpanels;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import org.slendersnax.waddup.config.SaveConfigPanel;
import org.slendersnax.waddup.config.LoadConfigPanel;
import org.slendersnax.waddup.core.GZDoomLauncher;
import org.slendersnax.waddup.core.PropWrapper;
import org.slendersnax.waddup.core.SlenderConstants;
import org.slendersnax.waddup.core.WADComponent;
import org.slendersnax.waddup.wad_display.WADPanel;

public class PickerPanel extends JPanel {
    private final SaveConfigPanel saveConfigPanel;
    private final LoadConfigPanel loadConfigPanel;
    private WADPanel wadContainer;

    private final JFrame mainFrame;
    private JPanel btnContainer, midCardPanel;
    private JButton btn_play, btn_mainMenu;
    private CardLayout cl;
    private final Dimension mainFrameSize;

    private final String basePath;
    private final String saveCardCode, loadCardCode, wadCardCode;
    private final String osname;
    private final GZDoomLauncher launcher;
    private final PropWrapper propWrapper;

    public PickerPanel(JFrame _mainFrame, Dimension frameSize) {
        mainFrame = _mainFrame;
        mainFrameSize = frameSize;

        saveCardCode = "SAVE";
        loadCardCode = "LOAD";
        wadCardCode = "WAD";

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(mainFrameSize);
        setMaximumSize(mainFrameSize);
        setSize(mainFrameSize);

        propWrapper = new PropWrapper();

        basePath = propWrapper.getProperty(PropWrapper.FILE_SETTINGS_INDEX, SlenderConstants.SETTINGS_WAD_DIRECTORY);
        saveConfigPanel = new SaveConfigPanel(frameSize);
        loadConfigPanel = new LoadConfigPanel(frameSize);

        btnContainer = new JPanel();
        wadContainer = new WADPanel(mainFrame, mainFrameSize, basePath);
        midCardPanel = new JPanel();
        cl = new CardLayout();

        btn_play = new JButton("Play");
        btn_mainMenu = new JButton("Main Menu");

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

        addComponents();
        initBtnActions();

        cl.show(midCardPanel, wadCardCode);
    }

    public JButton getBtn_mainMenu() {
        return btn_mainMenu;
    }

    public void initBtnActions() {
        wadContainer.getBtn_saveConfig().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!wadContainer.getWadListPanel().getItemList().isEmpty() && !wadContainer.getIwadLabel().getIwadPath().isEmpty()) {
                    cl.show(midCardPanel, saveCardCode);
                    saveConfigPanel.setConfigData(wadContainer.getWadListPanel().getItemList(), wadContainer.getIwadLabel().getIwadPath());
                }
            }
        });

        wadContainer.getBtn_loadConfig().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadConfigPanel.loadConfig();
                cl.show(midCardPanel, loadCardCode);
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

        saveConfigPanel.getBtn_cancelSave().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cl.show(midCardPanel, wadCardCode);
            }
        });

        loadConfigPanel.getBtn_cancelLoad().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cl.show(midCardPanel, wadCardCode);
            }
        });

        saveConfigPanel.getBtn_saveConfig().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveConfigPanel.saveConfig();
                cl.show(midCardPanel, wadCardCode);
            }
        });

        loadConfigPanel.getBtn_loadConfig().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cl.show(midCardPanel, wadCardCode);
                ArrayList<WADComponent> selectedConfigWads = loadConfigPanel.getLoadedWads();

                if (!selectedConfigWads.isEmpty()) {
                    wadContainer.getIwadLabel().setIWADprops(selectedConfigWads.get(0).getTitle(), selectedConfigWads.get(0).sWADPath);
                    selectedConfigWads.remove(0);

                    wadContainer.getWadListPanel().setItemList(selectedConfigWads);
                }
            }
        });
    }

    public void showDefaultCard() {
        cl.show(midCardPanel, wadCardCode);
    }

    public void addComponents() {
        btnContainer.add(btn_mainMenu, BorderLayout.WEST);
        btnContainer.add(btn_play, BorderLayout.EAST);

        midCardPanel.setSize(new Dimension(mainFrameSize.width, (int)(mainFrameSize.height * 0.8)));
        midCardPanel.setLayout(cl);
        midCardPanel.add(saveConfigPanel, saveCardCode);
        midCardPanel.add(loadConfigPanel, loadCardCode);
        midCardPanel.add(wadContainer, wadCardCode);

        add(midCardPanel);
        add(btnContainer);
    }
}