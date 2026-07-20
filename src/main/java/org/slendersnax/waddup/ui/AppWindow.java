package org.slendersnax.waddup.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import org.slendersnax.waddup.model.ApplicationContext;
import org.slendersnax.waddup.model.Settings;
import org.slendersnax.waddup.repository.SettingsRepository;
import org.slendersnax.waddup.ui.helpers.NavigationHandler;

public class AppWindow extends JFrame implements ComponentListener, NavigationHandler {
    private final JPanel wrapperPanel;
    private final PickerPanel pickerPanel;
    private final OptionsPanel optionsPanel;
    private final CardLayout mainCL;
    private final ApplicationContext applicationContext;
    private final Settings settings;
    private final SettingsRepository settingsRepository;

    private final String codeWadPicker, codeSettings;

    public AppWindow(ApplicationContext applicationContext) {
        super("WADdup");

        this.applicationContext = applicationContext;
        this.settings = this.applicationContext.getSettings();
        this.settingsRepository = this.applicationContext.getSettingsRepository();

        // finding out the display resolution of the monitor (or main monitor in the case of multi-monitor setups)
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        Dimension mainFrameDimension = new Dimension(settings.getPreferredWidth(), settings.getPreferredHeight());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(mainFrameDimension);
        setLocationRelativeTo(null); // centers it on screen automatically
        addComponentListener(this);

        wrapperPanel = new JPanel();
        pickerPanel = new PickerPanel(applicationContext.getSettings(), applicationContext.getWadSession(), applicationContext.getConfigRepository());
        optionsPanel = new OptionsPanel(applicationContext.getSettings(), applicationContext.getSettingsRepository());

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
        settings.setPreferredWidth(this.getWidth());
        settings.setPreferredHeight(this.getHeight());

        settingsRepository.save(settings);
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
