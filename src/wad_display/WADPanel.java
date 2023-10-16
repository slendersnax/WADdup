package wad_display;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WADPanel extends JPanel {
    public ArrayList<WADComponent> wadList;
    GridBagConstraints constr;

    public WADPanel() {
        wadList = new ArrayList<WADComponent>();

        setLayout(new GridBagLayout());
        constr = new GridBagConstraints();
        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.weightx = 1;
        constr.gridx = 0;

        constr.anchor = GridBagConstraints.FIRST_LINE_START;
    }

    public void addWad(String wadName, String wadPath, JFrame parentFrame) {
        WADComponent newWad = new WADComponent(wadName, wadPath);
        wadList.add(newWad);
        add(newWad, constr);

        parentFrame.revalidate();
        parentFrame.repaint();

        newWad.btn_remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wadList.remove(newWad);
                remove(newWad);
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });
    }
}
