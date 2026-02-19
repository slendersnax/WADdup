package org.slendersnax.waddup.mainpanels;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import org.slendersnax.waddup.core.SlenderConstants;
import org.slendersnax.waddup.core.PropWrapper;

public class AppWindow extends JFrame implements ComponentListener {
    private final JPanel wrapperPanel;
    private final PickerPanel pickerPanel;
    private final OptionsPanel optionsPanel;
    private final CardLayout mainCL;
    private final PropWrapper propWrapper;

    private final String codeWadPicker, codeSettings;

    public AppWindow() {
        super("WADdup");

        // finding out the display resolution of the monitor (or main monitor in the case of multi-monitor setups)
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        // i just like this size
        Dimension mainFrameDimension = new Dimension((int)(width / 2), (int)(height / 1.5));
        propWrapper = new PropWrapper();

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
        pickerPanel = new PickerPanel(this, mainFrameDimension);
        optionsPanel = new OptionsPanel(this, mainFrameDimension);

        mainCL = new CardLayout();

        codeWadPicker = "WAD_PICKER";
        codeSettings = "SETTINGS";

        wrapperPanel.setLayout(mainCL);
        wrapperPanel.add(pickerPanel, codeWadPicker);
        wrapperPanel.add(optionsPanel, codeSettings);

        add(wrapperPanel);
        mainCL.show(wrapperPanel, codeWadPicker);
        initBtnActions();

        setVisible(true);
    }

    private void initBtnActions() {
        pickerPanel.getBtnSettings().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainCL.show(wrapperPanel, codeSettings);
                pickerPanel.showDefaultCard();
            }
        });

        optionsPanel.getBtnWadPanel().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainCL.show(wrapperPanel, codeWadPicker);
                optionsPanel.showDefaultCard();
            }
        });
    }

    public void componentHidden(ComponentEvent ce) {};
    public void componentShown(ComponentEvent ce) {};
    public void componentMoved(ComponentEvent ce) {};

    public void componentResized(ComponentEvent ce) {
        propWrapper.storeProperty(PropWrapper.FILE_SETTINGS_INDEX, SlenderConstants.SETTINGS_PREF_WIDTH, Integer.toString(this.getWidth()));
        propWrapper.storeProperty(PropWrapper.FILE_SETTINGS_INDEX, SlenderConstants.SETTINGS_PREF_HEIGHT, Integer.toString(this.getHeight()));
    };
}
