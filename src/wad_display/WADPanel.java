package wad_display;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class WADPanel extends JPanel {
    public ArrayList<WADComponent> wadList;

    public WADPanel() {
        wadList = new ArrayList<WADComponent>();

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

    public void addWad(String wadName, String wadPath, JFrame parentFrame) {
        WADComponent newWad = new WADComponent(wadName, wadPath);
        wadList.add(newWad);

        add(newWad);

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

        newWad.btn_moveup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = wadList.indexOf(newWad);

                if(index > 0) {
                    Collections.swap(wadList, index, index - 1);
                    remove(newWad);
                    add(newWad, index - 1);
                }

                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });

        newWad.btn_movedown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = wadList.indexOf(newWad);

                if(index < wadList.size() - 1) {
                    Collections.swap(wadList, index, index + 1);
                    remove(newWad);
                    add(newWad, index + 1);
                }

                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });
    }
}
