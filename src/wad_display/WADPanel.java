package wad_display;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class WADPanel extends JPanel {
    public ArrayList<WADComponent> wadList;
    private JPanel panelWadContainer, panelBtnContainer;
    private JButton btn_remove, btn_moveup, btn_movedown;
    private JScrollPane pwadScroller;
    private int nSelectedIndex;

    public WADPanel() {
        wadList = new ArrayList<WADComponent>();
        panelWadContainer = new JPanel();
        panelBtnContainer = new JPanel();
        btn_remove = new JButton("remove");
        btn_moveup = new JButton("up");
        btn_movedown = new JButton("down");
        pwadScroller = new JScrollPane(panelWadContainer);
        nSelectedIndex = -1;

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        panelWadContainer.setLayout(new BoxLayout(panelWadContainer, BoxLayout.PAGE_AXIS));
        panelBtnContainer.setLayout(new BoxLayout(panelBtnContainer, BoxLayout.Y_AXIS));

        pwadScroller.setPreferredSize(new Dimension(500, 320));
        pwadScroller.getVerticalScrollBar().setUnitIncrement(16);

        panelBtnContainer.add(Box.createHorizontalGlue());
        panelBtnContainer.add(btn_moveup);
        panelBtnContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        panelBtnContainer.add(btn_movedown);
        panelBtnContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        panelBtnContainer.add(btn_remove);
        panelBtnContainer.add(Box.createHorizontalGlue());

        btn_remove.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_moveup.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_movedown.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(pwadScroller);
        add(Box.createHorizontalGlue());
        add(panelBtnContainer);

        btn_remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (nSelectedIndex != -1) {
                    WADComponent toRemove = wadList.get(nSelectedIndex);
                    Component parentComp = toRemove.getParent();
                    panelWadContainer.remove(toRemove);
                    wadList.remove(nSelectedIndex);

                    nSelectedIndex = -1;

                    revalidate();
                    repaint();
                }
            }
        });

        btn_moveup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (nSelectedIndex > 0) {
                    WADComponent toMove = wadList.get(nSelectedIndex);

                    Collections.swap(wadList, nSelectedIndex, nSelectedIndex - 1);
                    panelWadContainer.remove(toMove);
                    panelWadContainer.add(toMove, nSelectedIndex - 1);
                    nSelectedIndex --;

                    revalidate();
                    repaint();
                }
            }
        });

        btn_movedown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (nSelectedIndex < wadList.size() - 1 && nSelectedIndex != -1) {
                    WADComponent toMove = wadList.get(nSelectedIndex);

                    Collections.swap(wadList, nSelectedIndex, nSelectedIndex + 1);
                    panelWadContainer.remove(toMove);
                    panelWadContainer.add(toMove, nSelectedIndex + 1);
                    nSelectedIndex ++;

                    revalidate();
                    repaint();
                }
            }
        });
    }

    public void addWad(String wadName, String wadPath, JFrame parentFrame) {
        WADComponent newWad = new WADComponent(wadName, wadPath);
        wadList.add(newWad);

        panelWadContainer.add(newWad);

        parentFrame.revalidate();
        parentFrame.repaint();

        newWad.btn_select.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (nSelectedIndex != -1) {
                    wadList.get(nSelectedIndex).resetBorder();
                }

                int newIndex = wadList.indexOf(newWad);

                if (nSelectedIndex != newIndex) {
                    newWad.setBorder(BorderFactory.createLineBorder(new Color(0, 125, 167), 2));
                    nSelectedIndex = wadList.indexOf(newWad);
                }
                // deselecting the item
                else {
                    newWad.resetBorder();
                    nSelectedIndex = -1;
                }

                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });
    }
}
