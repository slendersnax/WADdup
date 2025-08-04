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
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Dimension;
import java.awt.dnd.DropTargetListener;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DnDConstants;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Arrays;

import org.slendersnax.waddup.core.VerticalBtnPanel;
import org.slendersnax.waddup.core.ItemPanel;
import org.slendersnax.waddup.core.WADModel;

public class WADPanel extends JPanel implements DropTargetListener {
    private final IWADLabel iwadLabel;
    private final ItemPanel<WADModel> wadListPanel;

    private final JFrame parentFrame;
    private final JPanel midPanel;
    private final VerticalBtnPanel panelBtnContainer;
    private final JButton btn_iwadPicker, btn_pwadPicker, btn_remove, btn_removeAll, btn_moveup, btn_movedown, btn_saveConfig, btn_loadConfig;
    private final JLabel move_header;
    private JFileChooser fileChooser;
    private FileNameExtensionFilter wadFilter;
    private final Dimension stdHorizontalFiller;
    private final String basePath;

    public WADPanel(JFrame _parentFrame, Dimension _parentFrameSize, String _basePath) {
        parentFrame = _parentFrame;
        stdHorizontalFiller = new Dimension(5, 0);

        iwadLabel = new IWADLabel();
        wadListPanel = new ItemPanel<WADModel>(new Dimension((int)(_parentFrameSize.width * 0.80), (int)(_parentFrameSize.height * 0.80)), true);
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

        wadFilter = new FileNameExtensionFilter("DOOM mod files (wad, pk3, zip, deh)", "wad", "pk3", "zip", "deh");

        DropTarget dropTarget = new DropTarget(wadListPanel, this);

        addComponents();
        addBtnActions();
    }

    public IWADLabel getIwadLabel() {
        return iwadLabel;
    }

    public ItemPanel<WADModel> getWadListPanel() {
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
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(wadFilter);

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
                        String name = selFile.getName();
                        wadListPanel.addItem(new WADModel(name, selFile.getAbsolutePath(), name.substring(name.length() - 3)));
                    }
                }

                fileChooser.setMultiSelectionEnabled(false);
            }
        });
        btn_remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wadListPanel.removeSelectedItems();
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

    @Override
    public void dragEnter(DropTargetDragEvent dropTargetDragEvent) {

    }

    @Override
    public void dragOver(DropTargetDragEvent dropTargetDragEvent) {

    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dropTargetDragEvent) {

    }

    @Override
    public void dragExit(DropTargetEvent dropTargetEvent) {

    }

    // TODO: add drop support to IWAD separately
    // drag-and-drop support which only accepts listed file types
    @Override
    public void drop(DropTargetDropEvent dtde) {
        try {
            dtde.acceptDrop(DnDConstants.ACTION_COPY);

            Transferable transferable = dtde.getTransferable();

            if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);

                if (areValidFileTypes(files)) {
                    for (File file : files) {
                        String name = file.getName();
                        wadListPanel.addItem(new WADModel(name, file.getAbsolutePath(), name.substring(name.length() - 3)));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean areValidFileTypes(List<File> files) {
    String[] validExtensions = {".wad", ".pk3", ".zip", ".deh"}; // define valid file types
    for (File file : files) {
        String fileName = file.getName().toLowerCase();
        boolean isValid = Arrays.stream(validExtensions)
                                 .anyMatch(fileName::endsWith);
        if (!isValid) return false;
    }
    return true;
}
}
