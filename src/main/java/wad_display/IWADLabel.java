package wad_display;

import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import java.awt.Component;

public class IWADLabel extends JLabel {
    private String iwadPath;

    public IWADLabel() {
        super("[IWAD]");
        iwadPath = "";

        setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public String getIwadPath() {
        return iwadPath;
    }

    public void setIwadPath(String _iwadPath) {
        iwadPath = _iwadPath;
    }

    public void signalNoIWADError() {
        setText("PLEASE PICK AN IWAD FILE");
    }
}
