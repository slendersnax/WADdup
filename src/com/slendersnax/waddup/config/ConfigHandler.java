package com.slendersnax.waddup.config;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Enumeration;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.lang.Object;

import com.slendersnax.waddup.core.*;

public class ConfigHandler {
    private JFrame mainFrame;
    private JPanel loadBtnsPanel, innerLoadPanel, saveBtnsPanel;
    private JLabel nameInstr;
    private ItemPanel configListPanel;
    private JTextField nameInput;
    private String savePanelID, loadPanelID;
    private Dimension standardBtnDim;

    public JButton btn_saveConfig, btn_loadConfig, btn_cancelSave, btn_cancelLoad, btn_removeConfig;
    public JPanel savePanel, loadPanel;

    private ArrayList<WADComponent> wadList, loadedWads;
    private String iwadPath;
    private int nSelectedConfig;
    private Properties propHandler;
    private File configFile;

    public ConfigHandler(JFrame _mainFrame) {
        mainFrame = _mainFrame;

        savePanelID = "save card";
        loadPanelID = "load card";

        propHandler = new Properties();
        initConfigFile();

        loadedWads = new ArrayList<WADComponent>();
        standardBtnDim = new Dimension(110, 28);

        savePanel = new JPanel();
        loadPanel = new JPanel();
        configListPanel = new ItemPanel(new Dimension(520, 420));
        innerLoadPanel = new JPanel();
        saveBtnsPanel = new JPanel();
        loadBtnsPanel = new JPanel();
        nameInstr = new JLabel("Enter the name of the config:");

        nameInput = new JTextField();
        btn_saveConfig = new JButton("save");
        btn_loadConfig = new JButton("load");
        btn_removeConfig = new JButton("remove");
        btn_cancelSave = new JButton("cancel");
        btn_cancelLoad = new JButton("cancel");

        btn_loadConfig.setMaximumSize(standardBtnDim);
        btn_removeConfig.setMaximumSize(standardBtnDim);
        btn_cancelLoad.setMaximumSize(standardBtnDim);

        nameInput.setMaximumSize(new Dimension(180, 20));
        loadBtnsPanel.setPreferredSize(standardBtnDim);

        savePanel.setLayout(new BoxLayout(savePanel, BoxLayout.Y_AXIS));
        loadPanel.setLayout(new BoxLayout(loadPanel, BoxLayout.Y_AXIS));
        innerLoadPanel.setLayout(new BoxLayout(innerLoadPanel, BoxLayout.LINE_AXIS));
        saveBtnsPanel.setLayout(new BoxLayout(saveBtnsPanel, BoxLayout.LINE_AXIS));
        loadBtnsPanel.setLayout(new BoxLayout(loadBtnsPanel, BoxLayout.Y_AXIS));

        nameInstr.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_saveConfig.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_loadConfig.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_removeConfig.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_cancelSave.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_cancelLoad.setAlignmentX(Component.CENTER_ALIGNMENT);

        savePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveBtnsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        saveBtnsPanel.add(btn_saveConfig);
        saveBtnsPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        saveBtnsPanel.add(btn_cancelSave);

        savePanel.add(Box.createVerticalGlue());
        savePanel.add(nameInstr);
        savePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        savePanel.add(nameInput);
        savePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        savePanel.add(saveBtnsPanel);
        savePanel.add(Box.createVerticalGlue());

        loadBtnsPanel.add(btn_loadConfig);
        loadBtnsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        loadBtnsPanel.add(btn_removeConfig);
        loadBtnsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        loadBtnsPanel.add(btn_cancelLoad);

        innerLoadPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        innerLoadPanel.add(configListPanel);
        innerLoadPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        innerLoadPanel.add(loadBtnsPanel);
        innerLoadPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        loadPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        loadPanel.add(innerLoadPanel);

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
                btn_saveConfig.setEnabled(!nameInput.getText().isEmpty());
            }
        });

        btn_removeConfig.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = configListPanel.getSelected();

                if (index != -1) {
                    loadConfigsFromXML();

                    propHandler.remove(configListPanel.getItemList().get(index).getTitle());
                    storeConfigsToXML();

                    configListPanel.removeItem(index);
                    configListPanel.setSelected(-1);
                }
            }
        });
    }

    private void initConfigFile() {
        String osname = System.getProperty("os.name");
        String homedir = System.getProperty("user.home");
        File configdir;

        if (osname.contains("Linux")) {
            configdir = new File(homedir.concat("/.config/waddup"));

            if(!configdir.exists()) {
                configdir.mkdirs();
            }

            configFile = new File(homedir.concat("/.config/waddup/configs.xml"));
        }
        else if (osname.contains("Windows")) {
            configdir = new File(homedir.concat("\\AppData\\waddup"));

            if(!configdir.exists()) {
                configdir.mkdirs();
            }

            configFile = new File(homedir.concat("\\AppData\\waddup\\configs.xml"));
        }
        else {
            configFile = new File("configs.xml");
        }

        try {
            configFile.createNewFile();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    private void loadConfigsFromXML() {
        try {
            if (configFile.exists()) {
                propHandler.loadFromXML(new FileInputStream(configFile.getAbsolutePath()));
            }
        } catch(IOException ex) {

        }
    }

    private void storeConfigsToXML() {
        try {
            if (configFile.exists()) {
                propHandler.storeToXML(new FileOutputStream(configFile.getAbsolutePath()), "");
            }
        } catch(IOException ex) {

        }
    }

    public void setConfigData(ArrayList<WADComponent> _wadList, String _iwadPath) {
        nameInput.setText("");
        btn_saveConfig.setEnabled(false);

        wadList = _wadList;
        iwadPath = _iwadPath;
    }

    public void saveConfig() throws IOException {
        ArrayList<String> wadPaths = new ArrayList<String>(wadList.stream().map(item -> item.sWADPath).toList());
        wadPaths.add(0, iwadPath);
        String joinedWadPaths = String.join(";;", wadPaths);

        loadConfigsFromXML();

        propHandler.setProperty(nameInput.getText(), joinedWadPaths);
        storeConfigsToXML();
    }

    public void loadConfig() {
        loadConfigsFromXML();
        Enumeration<Object> propKeys = propHandler.keys();
        configListPanel.clearItemList();
        configListPanel.setSelected(-1);

        while(propKeys.hasMoreElements()) {
            configListPanel.addItem(propKeys.nextElement().toString(), "");
        }
    }
    public ArrayList<WADComponent> getLoadedWads() {
        loadedWads.clear();
        loadConfigsFromXML();

        int selectedConfig = configListPanel.getSelected();

        if (selectedConfig != -1) {
            String selConfig = configListPanel.getItemList().get(configListPanel.getSelected()).getTitle();
            String selectedWads = propHandler.getProperty(selConfig);
            String[] arrSelWads = selectedWads.split(";;");

            for (String arrSelWad : arrSelWads) {
                loadedWads.add(new WADComponent(arrSelWad.substring(arrSelWad.lastIndexOf("/") + 1), arrSelWad, arrSelWad.substring(arrSelWad.length() - 3)));
            }
        }

        return loadedWads;
    }
}
