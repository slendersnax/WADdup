package org.slendersnax.waddup.ui;

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

import org.slendersnax.waddup.model.WADSession;
import org.slendersnax.waddup.model.WADModel;
import org.slendersnax.waddup.model.Settings;
import org.slendersnax.waddup.service.GZDoomLauncher;
import org.slendersnax.waddup.infrastructure.PropWrapper;
import org.slendersnax.waddup.infrastructure.SlenderConstants;
import org.slendersnax.waddup.exception.InvalidSessionException;
import org.slendersnax.waddup.ui.components.WADPanel;
import org.slendersnax.waddup.ui.helpers.NavigationHandler;

public class PickerPanel extends JPanel implements NavigationHandler {
    private final SaveConfigPanel saveConfigPanel;
    private final LoadConfigPanel loadConfigPanel;
    private WADPanel wadContainer;

    private JPanel panelBtnContainer, panelMidCard;
    private JButton btnPlay, btnSettings;
    private CardLayout cl;
    private final Dimension stdHGapSize, stdVGapSize, stdBtnSize;

    private final String saveCardCode, loadCardCode, wadCardCode;
    private final GZDoomLauncher launcher;
    private final Settings settings;
    private final WADSession wadSession;

    private boolean savedNewConfig;

    public PickerPanel(Settings settings, WADSession wadSession) {
        this.settings = settings;
        this.wadSession = wadSession;

        Dimension mainFrameSize = new Dimension(settings.getPreferredWidth(), settings.getPreferredHeight());

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(mainFrameSize);
        setMaximumSize(mainFrameSize);
        setSize(mainFrameSize);

        saveConfigPanel = new SaveConfigPanel(mainFrameSize);
        loadConfigPanel = new LoadConfigPanel(mainFrameSize);

        saveCardCode = "SAVE";
        loadCardCode = "LOAD";
        wadCardCode = "WAD";

        panelBtnContainer = new JPanel();
        wadContainer = new WADPanel(settings, wadSession);
        panelMidCard = new JPanel();
        cl = new CardLayout();

        btnPlay = new JButton("Play");
        btnSettings = new JButton("Settings");

        launcher = new GZDoomLauncher(settings);

        stdHGapSize = new Dimension(5, 0);
        stdVGapSize = new Dimension(0, 5);
        stdBtnSize = new Dimension((int)(mainFrameSize.width * 0.20), SlenderConstants.STD_BTN_HEIGHT);

        savedNewConfig = false;
        loadConfigPanel.loadConfig();

        addComponents(mainFrameSize);
        initBtnActions();

        showPanel(wadCardCode);
    }

    public void onSettingsRequested(ActionListener listener) {
        btnSettings.addActionListener(listener);
    }

    public void initBtnActions() {
        wadContainer.onSaveCurrentconfigRequested(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    saveCurrentConfig();
                }
                catch (InvalidSessionException ex) {
                    JOptionPane.showMessageDialog(getParent(), ex.getMessage(), "Failed to save config", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        wadContainer.onLoadConfigurationsRequested(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // we only load the config again if we save a new configuration
                // still not the MOST efficient, but nice
                if (savedNewConfig) {
                    loadConfigPanel.loadConfig();
                    savedNewConfig = false;
                }

                showPanel(loadCardCode);
            }
        });

        btnPlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    playCurrentSession();
                }
                catch (InvalidSessionException ex) {
                    JOptionPane.showMessageDialog(getParent(), ex.getMessage(), "Failed to launch", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        saveConfigPanel.onCancelSaveRequested(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPanel(wadCardCode);
            }
        });

        saveConfigPanel.onSaveConfigRequested(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveConfigPanel.saveConfig();
                showPanel(wadCardCode);
            }
        });

        loadConfigPanel.onCancelLoadRequested(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPanel(wadCardCode);
            }
        });

        loadConfigPanel.onLoadConfigRequested(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPanel(wadCardCode);
                ArrayList<WADModel> selectedConfigWads = loadConfigPanel.getLoadedWads();

                if (!selectedConfigWads.isEmpty()) {
                    wadContainer.loadSession(selectedConfigWads);
                }
            }
        });
    }

    public void addComponents(Dimension mainFrameSize) {
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

    private void playCurrentSession() throws InvalidSessionException {
        wadSession.validateSession();

        launcher.run(wadSession);
    }

    private void saveCurrentConfig() throws InvalidSessionException {
        wadSession.validateSession();

        saveConfigPanel.setConfigData(wadSession.getpWADs(), wadSession.getiWAD().sWADPath);
        savedNewConfig = true;

        showPanel(saveCardCode);
    }

    @Override
    public void showPanel(String name) {
        switch(name) {
            case "WAD":
                cl.show(panelMidCard, wadCardCode);
                break;
            case "SAVE":
                cl.show(panelMidCard, saveCardCode);
                break;
            case "LOAD":
                cl.show(panelMidCard, loadCardCode);
                break;
            default:
                break;
        }
    }

    public void showDefaultCard() {
        showPanel(wadCardCode);
    }
}