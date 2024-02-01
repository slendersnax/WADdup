package org.slendersnax.waddup.wad_display;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Action;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import org.slendersnax.waddup.core.VerticalBtnPanel;
import org.slendersnax.waddup.core.ItemPanel;

public class WADPanel extends JPanel {
    private final IWADLabel iwadLabel;
    private final ItemPanel wadListPanel;

    private final JFrame parentFrame;
    private final JPanel midPanel;
    private final VerticalBtnPanel panelBtnContainer;
    private final JButton btn_iwadPicker, btn_pwadPicker, btn_remove, btn_removeAll, btn_moveup, btn_movedown, btn_saveConfig, btn_loadConfig;
    private final JLabel move_header;
    private JFileChooser fileChooser;
    private final Dimension stdVerticalFiller, stdHorizontalFiller;
    private final String basePath;

    public WADPanel(JFrame _parentFrame, Dimension _parentFrameSize, String _basePath) {
        parentFrame = _parentFrame;
        stdVerticalFiller = new Dimension(0, 5);
        stdHorizontalFiller = new Dimension(5, 0);

        iwadLabel = new IWADLabel();
        wadListPanel = new ItemPanel(new Dimension((int)(_parentFrameSize.width * 0.80), (int)(_parentFrameSize.height * 0.80)));
        panelBtnContainer = new VerticalBtnPanel(new Dimension((int)(_parentFrameSize.width * 0.20), (int)(_parentFrameSize.height * 0.80)));
        midPanel = new JPanel();

        btn_iwadPicker = new JButton("Pick IWAD");
        btn_pwadPicker = new JButton("Pick PWADS");
        btn_remove = new JButton("remove");
        btn_removeAll = new JButton("remove all");
        btn_moveup = new JButton("move up");
        btn_movedown = new JButton("move down");
        btn_saveConfig = new JButton("save config");
        btn_loadConfig = new JButton("load config");

        move_header = new JLabel("PWAD ops");
        move_header.setHorizontalAlignment(SwingConstants.CENTER);

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

    public JButton getBtn_saveConfig() {
        return btn_saveConfig;
    }

    public JButton getBtn_loadConfig() {
        return btn_loadConfig;
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
        panelBtnContainer.addElem(btn_pwadPicker);
        panelBtnContainer.addElem(btn_saveConfig);
        panelBtnContainer.addElem(btn_loadConfig);
        panelBtnContainer.add(Box.createVerticalGlue());
        panelBtnContainer.addElem(move_header);
        panelBtnContainer.addElem(btn_moveup);
        panelBtnContainer.addElem(btn_movedown);
        panelBtnContainer.addElem(btn_remove);
        panelBtnContainer.addLastElem(btn_removeAll);

        midPanel.add(Box.createRigidArea(stdHorizontalFiller));
        midPanel.add(wadListPanel);
        midPanel.add(Box.createRigidArea(stdHorizontalFiller));
        midPanel.add(panelBtnContainer);
        midPanel.add(Box.createRigidArea(stdHorizontalFiller));

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
                wadListPanel.removeSelectedItem();
            }
        });

        btn_removeAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wadListPanel.clearItemList();
                iwadLabel.resetIWADprops();
            }
        });

        btn_moveup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wadListPanel.moveSelectedItemBack();
            }
        });

        btn_movedown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wadListPanel.moveSelectedItemForward();
            }
        });
    }
}
