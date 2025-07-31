package org.slendersnax.waddup.mainpanels;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.Box;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import org.slendersnax.waddup.config.SaveConfigPanel;
import org.slendersnax.waddup.config.LoadConfigPanel;
import org.slendersnax.waddup.core.GZDoomLauncher;
import org.slendersnax.waddup.core.PropWrapper;
import org.slendersnax.waddup.core.SlenderConstants;
import org.slendersnax.waddup.core.WADModel;
import org.slendersnax.waddup.wad_display.WADPanel;

public class PickerPanel extends JPanel {
    private final SaveConfigPanel saveConfigPanel;
    private final LoadConfigPanel loadConfigPanel;
    private WADPanel wadContainer;

    private final JFrame mainFrame;
    private JPanel panelBtnContainer, panelMidCard;
    private JButton btnPlay, btnSettings;
    private CardLayout cl;
    private final Dimension mainFrameSize, stdHGapSize, stdVGapSize, stdBtnSize;

    private final String basePath;
    private final String saveCardCode, loadCardCode, wadCardCode;
    private final String osname;
    private final GZDoomLauncher launcher;
    private final PropWrapper propWrapper;

    private boolean savedNewConfig;

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

        panelBtnContainer = new JPanel();
        wadContainer = new WADPanel(mainFrame, mainFrameSize, basePath);
        panelMidCard = new JPanel();
        cl = new CardLayout();

        btnPlay = new JButton("Play");
        btnSettings = new JButton("Settings");

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

        stdHGapSize = new Dimension(5, 0);
        stdVGapSize = new Dimension(0, 5);
        stdBtnSize = new Dimension((int)(frameSize.width * 0.20), SlenderConstants.STD_BTN_HEIGHT);

        savedNewConfig = false;
        loadConfigPanel.loadConfig();

        addComponents();
        initBtnActions();

        cl.show(panelMidCard, wadCardCode);
    }

    public JButton getBtnSettings() {
        return btnSettings;
    }

    public void initBtnActions() {
        wadContainer.getBtn_saveConfig().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!wadContainer.getWadListPanel().getItemList().isEmpty() && !wadContainer.getIwadLabel().getIwadPath().isEmpty()) {
                    cl.show(panelMidCard, saveCardCode);
                    saveConfigPanel.setConfigData(wadContainer.getWadListPanel().getItemList(), wadContainer.getIwadLabel().getIwadPath());
                    savedNewConfig = true;
                }
                else {
                    JOptionPane.showMessageDialog(mainFrame, "No IWAD selected or no PWADS selected", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        wadContainer.getBtn_loadConfig().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // we only load the config again if we save a new configuration
                // still not the MOST efficient, but nice
                if (savedNewConfig) {
                    loadConfigPanel.loadConfig();
                    savedNewConfig = false;
                }

                cl.show(panelMidCard, loadCardCode);
            }
        });

        btnPlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<WADModel> sessionWads = wadContainer.getWadListPanel().getItemList();

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
                cl.show(panelMidCard, wadCardCode);
            }
        });

        loadConfigPanel.getBtn_cancelLoad().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cl.show(panelMidCard, wadCardCode);
            }
        });

        saveConfigPanel.getBtn_saveConfig().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveConfigPanel.saveConfig();
                cl.show(panelMidCard, wadCardCode);
            }
        });

        loadConfigPanel.getBtn_loadConfig().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cl.show(panelMidCard, wadCardCode);
                ArrayList<WADModel> selectedConfigWads = loadConfigPanel.getLoadedWads();

                if (!selectedConfigWads.isEmpty()) {
                    wadContainer.getIwadLabel().setIWADprops(selectedConfigWads.get(0).sWadTitle, selectedConfigWads.get(0).sWADPath);
                    selectedConfigWads.remove(0);

                    wadContainer.getWadListPanel().setItemList(selectedConfigWads);
                }
            }
        });
    }

    public void showDefaultCard() {
        cl.show(panelMidCard, wadCardCode);
    }

    public void addComponents() {
        btnSettings.setMaximumSize(stdBtnSize);
        btnSettings.setPreferredSize(stdBtnSize);
        btnPlay.setMaximumSize(stdBtnSize);
        btnPlay.setPreferredSize(stdBtnSize);

        panelBtnContainer.setLayout(new BoxLayout(panelBtnContainer, BoxLayout.X_AXIS));
        panelBtnContainer.add(Box.createRigidArea(stdHGapSize));
        panelBtnContainer.add(btnSettings);
        panelBtnContainer.add(Box.createHorizontalGlue());
        panelBtnContainer.add(btnPlay);
        panelBtnContainer.add(Box.createRigidArea(stdHGapSize));

        panelMidCard.setSize(new Dimension(mainFrameSize.width, (int)(mainFrameSize.height * 0.8)));
        panelMidCard.setLayout(cl);
        panelMidCard.add(saveConfigPanel, saveCardCode);
        panelMidCard.add(loadConfigPanel, loadCardCode);
        panelMidCard.add(wadContainer, wadCardCode);

        add(panelMidCard);
        add(Box.createRigidArea(stdVGapSize));
        add(panelBtnContainer);
        add(Box.createRigidArea(stdVGapSize));
    }
}