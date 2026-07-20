package org.slendersnax.waddup.ui;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Component;
import java.util.ArrayList;

import org.slendersnax.waddup.model.WADModel;
import org.slendersnax.waddup.model.Config;
import org.slendersnax.waddup.model.WADSession;
import org.slendersnax.waddup.repository.ConfigRepository;

public class SaveConfigPanel extends JPanel {
    private JPanel saveBtnsPanel;
    private JLabel nameInstr;
    private JTextField nameInput;
    private JButton btn_saveConfig, btn_cancelSave;

    private ArrayList<WADModel> wadList;
    private String iwadPath;
    private final ConfigRepository configRepository;
    private final ArrayList<Config> configs;
    private final WADSession wadSession;

    public SaveConfigPanel(Dimension size, ConfigRepository configRepository, ArrayList<Config> configs, WADSession wadSession) {
        this.configRepository = configRepository;
        this.configs = configs;
        this.wadSession = wadSession;

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

    public void onSaveConfigRequested(ActionListener listener) {
        btn_saveConfig.addActionListener(listener);
    }

    public void onCancelSaveRequested(ActionListener listener) {
        btn_cancelSave.addActionListener(listener);
    }

    public void resetConfigUI() {
        nameInput.setText("");
        btn_saveConfig.setEnabled(false);
    }

    public void saveConfig() {
        // check if this config name already exists
        // if so, ask the user if they want to overwrite existing config
        Config matchConfig = configs.stream()
                    .filter(config -> config.getName().equals(nameInput.getText()))
                    .findFirst()
                    .orElse(null);

        // found config with this name
        if (matchConfig != null) {
            int reply = JOptionPane.showConfirmDialog(getParent(), "A configuration with this name already exists. Do you want to overwrite it?", "Name conflict", JOptionPane.YES_NO_OPTION);

            if (reply == JOptionPane.NO_OPTION) {
                return;
            }

            matchConfig.setWadSession(wadSession);
        }
        else {
            configs.add(new Config(nameInput.getText(), new WADSession(wadSession)));
        }

        configRepository.save(configs);
    }
}
