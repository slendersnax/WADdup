package wad_display;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.BorderFactory;
import java.awt.Dimension;

public class WADComponent extends JPanel {
    JLabel wadTitle;
    public JButton btn_select;
    public String sWADPath, sFileType;

    public WADComponent(String sWadTitle, String _sWADPath, String _sFileType) {
        wadTitle = new JLabel(sWadTitle);
        btn_select = new JButton("select");

        sWADPath = _sWADPath;
        sFileType = _sFileType;

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        add(Box.createRigidArea(new Dimension(5, 0)));
        add(wadTitle);

        // use this to put the buttons all the way to the right
        add(Box.createHorizontalGlue());

        add(btn_select);
        add(Box.createRigidArea(new Dimension(5, 0)));
    }

    public void resetBorder() {
        setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    }
}
