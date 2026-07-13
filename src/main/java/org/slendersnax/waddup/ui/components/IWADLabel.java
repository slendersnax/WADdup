package org.slendersnax.waddup.ui.components;

import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.Component;

public class IWADLabel extends JLabel {
    public IWADLabel() {
        super("[no IWAD selected]");

        setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public void setIWAD(String iwadName) {
        setText("IWAD: ".concat(iwadName));
    }

    public void resetIWAD() {
        setText("[no IWAD selected]");
    }
}
