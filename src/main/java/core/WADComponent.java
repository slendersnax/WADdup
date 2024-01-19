package core;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import java.awt.Color;

public class WADComponent extends JPanel {
    private JLabel wadTitle;
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

    public String getTitle() {
        return wadTitle.getText();
    }

    public void setSelected() {
        setBorder(BorderFactory.createLineBorder(new Color(0, 125, 167), 2));
        btn_select.setText("deselect");
    }

    public void setDeselected() {
        setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        btn_select.setText("select");
    }
}
