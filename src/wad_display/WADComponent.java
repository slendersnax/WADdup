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
    public JButton btn_remove, btn_moveup, btn_movedown;
    public String sWADPath;

    public WADComponent(String sWadTitle, String _sWADPath) {
        wadTitle = new JLabel(sWadTitle);
        btn_remove = new JButton("remove");
        btn_moveup = new JButton("up");
        btn_movedown = new JButton("down");
        sWADPath = _sWADPath;

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        add(Box.createRigidArea(new Dimension(5, 0)));
        add(wadTitle);

        // use this to put the buttons all the way to the right
        add(Box.createHorizontalGlue());

        add(btn_moveup);
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(btn_movedown);
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(btn_remove);
        add(Box.createRigidArea(new Dimension(5, 0)));
    }
}
