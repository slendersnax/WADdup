package wad_display;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;

public class WADComponent extends JPanel {
    JLabel wadTitle;
    public JButton btn_remove;
    public String sWADPath;

    public WADComponent(String sWadTitle, String _sWADPath) {
        wadTitle = new JLabel(sWadTitle);
        btn_remove = new JButton("Remove");
        sWADPath = _sWADPath;

        add(wadTitle, BorderLayout.WEST);
        add(btn_remove, BorderLayout.EAST);
    }
}
