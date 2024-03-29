package org.slendersnax.waddup.wad_display;

import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.Component;

public class IWADLabel extends JLabel {
    private String iwadPath;

    public IWADLabel() {
        super("[IWAD]");
        iwadPath = "";

        setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public void setIWADprops(String iwadName, String _iwadPath) {
        setText("IWAD: ".concat(iwadName));
        setIwadPath(_iwadPath);
    }

    public void resetIWADprops() {
        setText("[IWAD]");
        setIwadPath("");
    }

    public String getIwadPath() {
        return iwadPath;
    }

    public void setIwadPath(String _iwadPath) {
        iwadPath = _iwadPath;
    }
}
