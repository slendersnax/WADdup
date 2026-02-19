package org.slendersnax.waddup.config;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Dimension;
import java.awt.Component;
import java.util.ArrayList;

import org.slendersnax.waddup.core.*;

public class SaveConfigPanel extends JPanel {
    private JPanel saveBtnsPanel;
    private JLabel nameInstr;
    private JTextField nameInput;
    private JButton btn_saveConfig, btn_cancelSave;

    private ArrayList<WADModel> wadList;
    private String iwadPath;
    private final PropWrapper propHandler;

    public SaveConfigPanel(Dimension size) {
        propHandler = new PropWrapper();

        saveBtnsPanel = new JPanel();
        nameInstr = new JLabel("Enter the name of the config:");

        nameInput = new JTextField();
        btn_saveConfig = new JButton("save");
        btn_cancelSave = new JButton("cancel");

        nameInput.setMaximumSize(new Dimension(size.width / 3, size.height / 240));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        saveBtnsPanel.setLayout(new BoxLayout(saveBtnsPanel, BoxLayout.LINE_AXIS));

        nameInstr.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_saveConfig.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_cancelSave.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveBtnsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        saveBtnsPanel.add(btn_saveConfig);
        saveBtnsPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        saveBtnsPanel.add(btn_cancelSave);

        add(Box.createVerticalGlue());
        add(nameInstr);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(nameInput);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(saveBtnsPanel);
        add(Box.createVerticalGlue());

        nameInput.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changed();
            }
            public void removeUpdate(DocumentEvent e) {
                changed();
            }
            public void insertUpdate(DocumentEvent e) {
                changed();
            }

            public void changed() {
                // at WADComponent we're getting the filetype of the items by taking the last three characters
                // so the name of the config has to be more than 3 chars long
                btn_saveConfig.setEnabled(nameInput.getText().length() > 3);
            }
        });
    }

    public JButton getBtn_saveConfig() {
        return btn_saveConfig;
    }

    public JButton getBtn_cancelSave() {
        return btn_cancelSave;
    }

    public void setConfigData(ArrayList<WADModel> _wadList, String _iwadPath) {
        nameInput.setText("");
        btn_saveConfig.setEnabled(false);

        wadList = _wadList;
        iwadPath = _iwadPath;
    }

    public void saveConfig() {
        // check if this config name already exists
        // if so, ask the user if they want to overwrite existing config
        if (propHandler.getProperty(PropWrapper.FILE_CONFIG_INDEX, nameInput.getText()) != null) {

            int reply = JOptionPane.showConfirmDialog(null, "A configuration with this name already exists. Do you want to overwrite it?", "Name conflict", JOptionPane.YES_NO_OPTION);

            if (reply == JOptionPane.NO_OPTION) {
                return;
            }
        }

        ArrayList<String> wadPaths = new ArrayList<String>();

        for (WADModel wad : wadList) {
            wadPaths.add(wad.sWADPath);
        }

        wadPaths.add(0, iwadPath);
        String joinedWadPaths = String.join(SlenderConstants.CONFIG_ITEM_SEPARATOR, wadPaths);

        propHandler.storeProperty(PropWrapper.FILE_CONFIG_INDEX, nameInput.getText(), joinedWadPaths);
    }
}
