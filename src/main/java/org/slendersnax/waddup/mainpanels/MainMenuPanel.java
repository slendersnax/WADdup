package org.slendersnax.waddup.mainpanels;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Component;

import org.slendersnax.waddup.core.VerticalBtnPanel;

public class MainMenuPanel extends JPanel {
    private JLabel labelTitle;
    private JButton btnStart, btnSettings;
    private VerticalBtnPanel btnContainer;

    public MainMenuPanel(Dimension mainFrameSize) {
        mainFrameSize.width = mainFrameSize.width / 5;
        btnContainer = new VerticalBtnPanel(mainFrameSize);

        labelTitle = new JLabel("WADdup");
        btnStart = new JButton("Start");
        btnSettings = new JButton("Settings");

        labelTitle.setHorizontalAlignment(SwingConstants.CENTER);

        btnContainer.add(Box.createVerticalGlue());
        btnContainer.addElem(labelTitle);
        btnContainer.addElem(btnStart);
        btnContainer.addElem(btnSettings);
        btnContainer.add(Box.createVerticalGlue());
        btnContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        add(btnContainer);
    }

    public JButton getBtnPlay() {
        return btnStart;
    }

    public JButton getBtnSettings() {
        return btnSettings;
    }
}
