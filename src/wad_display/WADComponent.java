package wad_display;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class WADComponent extends JPanel {
    JLabel wadTitle;
    public JButton btn_remove;
    public String sWADPath;
    GridBagConstraints constr;

    public WADComponent(String sWadTitle, String _sWADPath) {
        wadTitle = new JLabel(sWadTitle);
        btn_remove = new JButton("Remove");
        sWADPath = _sWADPath;

        setLayout(new GridBagLayout());
        constr = new GridBagConstraints();
        constr.insets = new Insets(5, 10, 0, 10);  // some padding
        constr.gridy = 0;

        // setting this all the way to the left
        constr.gridx = 0;
        constr.weightx = 0.2;
        constr.anchor = GridBagConstraints.WEST;
        add(wadTitle, constr);

        // setting this all the way to the right
        constr.gridx = 1;
        constr.weightx = 0.1;
        constr.anchor = GridBagConstraints.EAST;
        add(btn_remove, constr);
    }
}
