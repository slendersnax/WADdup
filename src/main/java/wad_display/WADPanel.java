package wad_display;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Action;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class WADPanel extends JPanel {
    public ArrayList<WADComponent> wadList;
    public IWADLabel iwadLabel;
    private JFrame parentFrame;
    private JPanel panelWadContainer, panelBtnContainer, midPanel;
    private JButton btn_iwadPicker, btn_pwadPicker, btn_remove, btn_moveup, btn_movedown;
    private JLabel move_header;
    private JScrollPane pwadScroller;
    private JFileChooser fileChooser;
    private Dimension standardBtnDim, standardFillerDim;
    private int nSelectedIndex;
    private String basePath;

    public WADPanel(JFrame _parentFrame, String _basePath) {
        parentFrame = _parentFrame;

        wadList = new ArrayList<WADComponent>();
        iwadLabel = new IWADLabel();
        panelWadContainer = new JPanel();
        panelBtnContainer = new JPanel();
        midPanel = new JPanel();

        btn_iwadPicker = new JButton("Pick IWAD");
        btn_pwadPicker = new JButton("Pick PWADS");
        btn_remove = new JButton("remove");
        btn_moveup = new JButton("move up");
        btn_movedown = new JButton("move down");

        move_header = new JLabel("PWAD ops");

        pwadScroller = new JScrollPane(panelWadContainer);

        standardBtnDim = new Dimension(110, 28);
        standardFillerDim = new Dimension(0, 5);
        nSelectedIndex = -1;
        basePath = _basePath;

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
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.LINE_AXIS));
        panelWadContainer.setLayout(new BoxLayout(panelWadContainer, BoxLayout.PAGE_AXIS));
        panelBtnContainer.setLayout(new BoxLayout(panelBtnContainer, BoxLayout.Y_AXIS));

        pwadScroller.setPreferredSize(new Dimension(480, 400));
        pwadScroller.getVerticalScrollBar().setUnitIncrement(16);

        fileChooser = new JFileChooser(new File(basePath));

        // viewing in details mode by default
        Action details = fileChooser.getActionMap().get("viewTypeDetails");
        details.actionPerformed(null);

        panelBtnContainer.add(btn_iwadPicker);
        panelBtnContainer.add(Box.createRigidArea(standardFillerDim));
        panelBtnContainer.add(btn_pwadPicker);
        panelBtnContainer.add(Box.createVerticalGlue());
        panelBtnContainer.add(move_header);
        panelBtnContainer.add(Box.createRigidArea(standardFillerDim));
        panelBtnContainer.add(btn_moveup);
        panelBtnContainer.add(Box.createRigidArea(standardFillerDim));
        panelBtnContainer.add(btn_movedown);
        panelBtnContainer.add(Box.createRigidArea(standardFillerDim));
        panelBtnContainer.add(btn_remove);

        btn_remove.setMaximumSize(standardBtnDim);
        btn_moveup.setMaximumSize(standardBtnDim);
        btn_movedown.setMaximumSize(standardBtnDim);
        btn_iwadPicker.setMaximumSize(standardBtnDim);
        btn_pwadPicker.setMaximumSize(standardBtnDim);

        btn_remove.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_moveup.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_movedown.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_iwadPicker.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_pwadPicker.setAlignmentX(Component.CENTER_ALIGNMENT);
        move_header.setAlignmentX(Component.CENTER_ALIGNMENT);

        midPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        midPanel.add(pwadScroller);
        midPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        midPanel.add(panelBtnContainer);
        midPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        add(iwadLabel);
        add(midPanel);
    }

    private void addBtnActions() {
        btn_iwadPicker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileChooser.showOpenDialog(parentFrame);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    iwadLabel.setText(file.getName());
                    iwadLabel.setIwadPath(file.getAbsolutePath());
                }
            }
        });

        btn_pwadPicker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser.setMultiSelectionEnabled(true);
                int returnVal = fileChooser.showOpenDialog(parentFrame);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    File[] selFiles = fileChooser.getSelectedFiles();

                    for(int i = 0; i < selFiles.length; i ++) {
                        addWad(selFiles[i].getName(), selFiles[i].getAbsolutePath());
                    }
                }

                fileChooser.setMultiSelectionEnabled(false);
            }
        });
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
    }
}
