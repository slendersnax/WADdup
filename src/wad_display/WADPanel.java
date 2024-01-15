package wad_display;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class WADPanel extends JPanel {
    public ArrayList<WADComponent> wadList;
    private JFrame parentFrame;
    private JPanel panelWadContainer, panelBtnContainer;
    private JButton btn_remove, btn_moveup, btn_movedown, btn_saveconfig, btn_loadconfig;
    private JLabel move_header, config_header;
    private JScrollPane pwadScroller;
    private Dimension standardBtnDim, standardFillerDim;
    private int nSelectedIndex;

    public WADPanel(JFrame _parentFrame) {
        parentFrame = _parentFrame;

        wadList = new ArrayList<WADComponent>();
        panelWadContainer = new JPanel();
        panelBtnContainer = new JPanel();

        btn_remove = new JButton("remove");
        btn_moveup = new JButton("move up");
        btn_movedown = new JButton("move down");
        btn_saveconfig = new JButton("save");
        btn_loadconfig = new JButton("load");

        move_header = new JLabel("PWAD ops");
        config_header = new JLabel("config ops");

        pwadScroller = new JScrollPane(panelWadContainer);

        standardBtnDim = new Dimension(110, 28);
        standardFillerDim = new Dimension(0, 5);
        nSelectedIndex = -1;

        addComponents();
        addBtnActions();
    }

    public void addWad(String wadName, String wadPath) {
        WADComponent newWad = new WADComponent(wadName, wadPath, wadName.substring(wadName.length() - 3));
        wadList.add(newWad);

        panelWadContainer.add(newWad);

        parentFrame.revalidate();
        parentFrame.repaint();

        newWad.btn_select.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // deselecting old button if it exists
                if (nSelectedIndex != -1) {
                    wadList.get(nSelectedIndex).resetBorder();
                    wadList.get(nSelectedIndex).btn_select.setText("select");
                }

                int newIndex = wadList.indexOf(newWad);

                if (nSelectedIndex != newIndex) {
                    newWad.setBorder(BorderFactory.createLineBorder(new Color(0, 125, 167), 2));
                    nSelectedIndex = wadList.indexOf(newWad);
                    wadList.get(nSelectedIndex).btn_select.setText("deselect");
                }
                // deselecting the item
                else {
                    newWad.resetBorder();
                    newWad.btn_select.setText("select");
                    nSelectedIndex = -1;
                }

                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });
    }

    public void addComponents() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        panelWadContainer.setLayout(new BoxLayout(panelWadContainer, BoxLayout.PAGE_AXIS));
        panelBtnContainer.setLayout(new BoxLayout(panelBtnContainer, BoxLayout.Y_AXIS));

        pwadScroller.setPreferredSize(new Dimension(480, 320));
        pwadScroller.getVerticalScrollBar().setUnitIncrement(16);

        panelBtnContainer.add(Box.createVerticalGlue());
        panelBtnContainer.add(move_header);
        panelBtnContainer.add(Box.createRigidArea(standardFillerDim));
        panelBtnContainer.add(btn_moveup);
        panelBtnContainer.add(Box.createRigidArea(standardFillerDim));
        panelBtnContainer.add(btn_movedown);
        panelBtnContainer.add(Box.createRigidArea(standardFillerDim));
        panelBtnContainer.add(btn_remove);
        panelBtnContainer.add(Box.createRigidArea(new Dimension(0, 10)));
        panelBtnContainer.add(config_header);
        panelBtnContainer.add(Box.createRigidArea(standardFillerDim));
        panelBtnContainer.add(btn_saveconfig);
        panelBtnContainer.add(Box.createRigidArea(standardFillerDim));
        panelBtnContainer.add(btn_loadconfig);
        panelBtnContainer.add(Box.createRigidArea(standardFillerDim));
        panelBtnContainer.add(Box.createVerticalGlue());

        btn_remove.setMaximumSize(standardBtnDim);
        btn_moveup.setMaximumSize(standardBtnDim);
        btn_movedown.setMaximumSize(standardBtnDim);
        btn_saveconfig.setMaximumSize(standardBtnDim);
        btn_loadconfig.setMaximumSize(standardBtnDim);

        btn_remove.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_moveup.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_movedown.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_saveconfig.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_loadconfig.setAlignmentX(Component.CENTER_ALIGNMENT);
        move_header.setAlignmentX(Component.CENTER_ALIGNMENT);
        config_header.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createRigidArea(new Dimension(5, 0)));
        add(pwadScroller);
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(panelBtnContainer);
        add(Box.createRigidArea(new Dimension(5, 0)));
    }

    private void addBtnActions() {
        btn_remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (nSelectedIndex != -1) {
                    WADComponent toRemove = wadList.get(nSelectedIndex);

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

        btn_saveconfig.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (wadList.size() > 0) {
                    // save config
                }
            }
        });
    }
}
