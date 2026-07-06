package org.slendersnax.waddup.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import org.slendersnax.waddup.ui.helpers.NavigationHandler;

import org.slendersnax.waddup.infrastructure.SlenderConstants;
import org.slendersnax.waddup.infrastructure.PropWrapper;

public class AppWindow extends JFrame implements ComponentListener, NavigationHandler {
    private final JPanel wrapperPanel;
    private final PickerPanel pickerPanel;
    private final OptionsPanel optionsPanel;
    private final CardLayout mainCL;
    private final PropWrapper propWrapper;

    private final String codeWadPicker, codeSettings;

    public AppWindow(PropWrapper propWrapper) {
        super("WADdup");

        this.propWrapper = propWrapper;

        // finding out the display resolution of the monitor (or main monitor in the case of multi-monitor setups)
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        // i just like this size
        Dimension mainFrameDimension = new Dimension((int)(width / 2), (int)(height / 1.5));

        if (propWrapper.getProperty(PropWrapper.FILE_SETTINGS_INDEX, SlenderConstants.SETTINGS_PREF_WIDTH) == null) {
            propWrapper.storeProperty(PropWrapper.FILE_SETTINGS_INDEX, SlenderConstants.SETTINGS_PREF_WIDTH, Integer.toString(mainFrameDimension.width));
            propWrapper.storeProperty(PropWrapper.FILE_SETTINGS_INDEX, SlenderConstants.SETTINGS_PREF_HEIGHT, Integer.toString(mainFrameDimension.height));
        }
        else {
            mainFrameDimension.width = Integer.parseInt(propWrapper.getProperty(PropWrapper.FILE_SETTINGS_INDEX, SlenderConstants.SETTINGS_PREF_WIDTH));
            mainFrameDimension.height = Integer.parseInt(propWrapper.getProperty(PropWrapper.FILE_SETTINGS_INDEX, SlenderConstants.SETTINGS_PREF_HEIGHT));
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(mainFrameDimension);
        setLocationRelativeTo(null); // centers it on screen automatically
        addComponentListener(this);

        wrapperPanel = new JPanel();
        pickerPanel = new PickerPanel(propWrapper);
        optionsPanel = new OptionsPanel(propWrapper);

        mainCL = new CardLayout();

        codeWadPicker = "WAD_PICKER";
        codeSettings = "SETTINGS";

        wrapperPanel.setLayout(mainCL);
        wrapperPanel.add(pickerPanel, codeWadPicker);
        wrapperPanel.add(optionsPanel, codeSettings);

        add(wrapperPanel);

        registerNavigation();

        showPanel(codeWadPicker);
        setVisible(true);
    }

    private void registerNavigation() {
        pickerPanel.onSettingsRequested(e -> showPanel(codeSettings));
        optionsPanel.onWadPanelRequested(e -> showPanel(codeWadPicker));
    }

    public void componentHidden(ComponentEvent ce) {};
    public void componentShown(ComponentEvent ce) {};
    public void componentMoved(ComponentEvent ce) {};

    public void componentResized(ComponentEvent ce) {
        propWrapper.storeProperty(PropWrapper.FILE_SETTINGS_INDEX, SlenderConstants.SETTINGS_PREF_WIDTH, Integer.toString(this.getWidth()));
        propWrapper.storeProperty(PropWrapper.FILE_SETTINGS_INDEX, SlenderConstants.SETTINGS_PREF_HEIGHT, Integer.toString(this.getHeight()));
    };

    @Override
    public void showPanel(String name) {
        switch(name) {
            case "SETTINGS":
                mainCL.show(wrapperPanel, codeSettings);
                pickerPanel.showDefaultCard();
                break;
            case "WAD_PICKER":
                mainCL.show(wrapperPanel, codeWadPicker);
                optionsPanel.showDefaultCard();
                break;
            default:
                break;
        }
    }
}
