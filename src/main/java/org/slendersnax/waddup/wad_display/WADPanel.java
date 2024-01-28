package org.slendersnax.waddup.wad_display;

import org.slendersnax.waddup.core.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Action;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class WADPanel extends JPanel {
    private final IWADLabel iwadLabel;
    private final ItemPanel wadListPanel;

    private final JFrame parentFrame;
    private final JPanel midPanel;
    private final VerticalBtnPanel panelBtnContainer;
    private final JButton btn_iwadPicker, btn_pwadPicker, btn_remove, btn_moveup, btn_movedown;
    private final JLabel move_header;
    private JFileChooser fileChooser;
    private final Dimension standardBtnDim, standardFillerDim;
    private int nSelectedIndex;
    private final String basePath;

    public WADPanel(JFrame _parentFrame, String _basePath) {
        parentFrame = _parentFrame;

        standardBtnDim = new Dimension(110, 28);
        standardFillerDim = new Dimension(0, 5);

        iwadLabel = new IWADLabel();
        wadListPanel = new ItemPanel(new Dimension(520, 400));
        panelBtnContainer = new VerticalBtnPanel(new Dimension(110, 400));
        midPanel = new JPanel();

        btn_iwadPicker = new JButton("Pick IWAD");
        btn_pwadPicker = new JButton("Pick PWADS");
        btn_remove = new JButton("remove");
        btn_moveup = new JButton("move up");
        btn_movedown = new JButton("move down");

        move_header = new JLabel("PWAD ops");

        nSelectedIndex = -1;
        basePath = _basePath;

        addComponents();
        addBtnActions();
    }

    public IWADLabel getIwadLabel() {
        return iwadLabel;
    }

    public ItemPanel getWadListPanel() {
        return wadListPanel;
    }

    public void addComponents() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.LINE_AXIS));

        fileChooser = new JFileChooser(new File(basePath));

        // viewing in details mode by default
        Action details = fileChooser.getActionMap().get("viewTypeDetails");
        details.actionPerformed(null);
        fileChooser.setFileHidingEnabled(false);

        panelBtnContainer.addElem(btn_iwadPicker);
        panelBtnContainer.add(btn_pwadPicker);
        panelBtnContainer.add(Box.createVerticalGlue());
        panelBtnContainer.add(move_header);
        panelBtnContainer.add(Box.createRigidArea(standardFillerDim));
        panelBtnContainer.addElem(btn_moveup);
        panelBtnContainer.addElem(btn_movedown);
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
        midPanel.add(wadListPanel);
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
                    iwadLabel.setIWADprops(file.getName(), file.getAbsolutePath());
                }
            }
        });

        btn_pwadPicker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser.setMultiSelectionEnabled(true);
                int returnVal = fileChooser.showOpenDialog(parentFrame);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File[] selFiles = fileChooser.getSelectedFiles();

                    for (File selFile : selFiles) {
                        wadListPanel.addItem(selFile.getName(), selFile.getAbsolutePath());
                    }
                }

                fileChooser.setMultiSelectionEnabled(false);
            }
        });
        btn_remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nSelectedIndex = wadListPanel.getSelected();

                if (nSelectedIndex != -1) {
                    wadListPanel.removeItem(nSelectedIndex);
                    wadListPanel.setSelected(-1);
                }
            }
        });

        btn_moveup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nSelectedIndex = wadListPanel.getSelected();

                if (nSelectedIndex > 0) {
                    wadListPanel.moveSelectedItemBack();
                    wadListPanel.setSelected(nSelectedIndex - 1);
                }
            }
        });

        btn_movedown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nSelectedIndex = wadListPanel.getSelected();

                if (nSelectedIndex != -1 && nSelectedIndex < wadListPanel.getItemList().size() - 1) {
                    wadListPanel.moveSelectedItemForward();
                    wadListPanel.setSelected(nSelectedIndex + 1);
                }
            }
        });
    }
}
