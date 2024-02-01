package org.slendersnax.waddup.mainpanels;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppWindow {
    private final JFrame mainFrame;
    private final JPanel wrapperPanel;
    private final MainMenuPanel mainMenuPanel;
    private final PickerPanel pickerPanel;
    private final OptionsPanel optionsPanel;
    private final CardLayout mainCL;

    private final String codeMainMenu, codeWadPicker, codeSettings;

    public AppWindow() {
        // finding out the display resolution of the monitor (or main monitor in the case of multi-monitor setups)
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        // i just like this size
        Dimension mainFrameDimension = new Dimension((int)(width / 3), (int)(height / 2.5));

        mainFrame = new JFrame("WADdup");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(mainFrameDimension);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null); // centers it on screen automatically

        wrapperPanel = new JPanel();
        pickerPanel = new PickerPanel(mainFrame, mainFrameDimension);
        optionsPanel = new OptionsPanel(mainFrame, mainFrameDimension);
        mainMenuPanel = new MainMenuPanel(mainFrameDimension);

        mainCL = new CardLayout();

        codeMainMenu = "MAIN_MENU";
        codeWadPicker = "WAD_PICKER";
        codeSettings = "SETTINGS";

        wrapperPanel.setLayout(mainCL);
        wrapperPanel.add(pickerPanel, codeWadPicker);
        wrapperPanel.add(mainMenuPanel, codeMainMenu);
        wrapperPanel.add(optionsPanel, codeSettings);

        mainFrame.add(wrapperPanel);
        mainCL.show(wrapperPanel, codeMainMenu);
        initBtnActions();

        mainFrame.setVisible(true);
    }

    private void initBtnActions() {
        pickerPanel.getBtn_mainMenu().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainCL.show(wrapperPanel, codeMainMenu);
                pickerPanel.showDefaultCard();
            }
        });

        optionsPanel.getBtnMainMenu().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainCL.show(wrapperPanel, codeMainMenu);
                optionsPanel.showDefaultCard();
            }
        });

        mainMenuPanel.getBtnPlay().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainCL.show(wrapperPanel, codeWadPicker);
            }
        });

        mainMenuPanel.getBtnSettings().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainCL.show(wrapperPanel, codeSettings);
            }
        });
    }
}
