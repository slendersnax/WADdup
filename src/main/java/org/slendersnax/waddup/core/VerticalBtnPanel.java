package org.slendersnax.waddup.core;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Dimension;

public class VerticalBtnPanel extends JPanel {
    private final Dimension verticalBtnSpace, standardBtnSize;

    public VerticalBtnPanel(Dimension _size) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        verticalBtnSpace = new Dimension(0, 5);
        standardBtnSize = new Dimension(_size.width, SlenderConstants.STD_BTN_HEIGHT);

        setSize(_size);
        setPreferredSize(_size);
        setMaximumSize(_size);
    }

    public void addElem(JComponent comp) {
        comp.setPreferredSize(standardBtnSize);
        comp.setSize(standardBtnSize);
        comp.setMaximumSize(standardBtnSize);

        add(comp);
        add(Box.createRigidArea(verticalBtnSpace));
    }

    public void addLastElem(JComponent comp) {
        comp.setPreferredSize(standardBtnSize);
        comp.setSize(standardBtnSize);
        comp.setMaximumSize(standardBtnSize);

        add(comp);
    }
}
