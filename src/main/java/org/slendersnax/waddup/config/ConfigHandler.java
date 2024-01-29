package org.slendersnax.waddup.config;

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
import java.util.stream.Collectors;

import org.slendersnax.waddup.core.*;

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
    private final Properties propHandler;
    private File configFile, settingsFile;

    public ConfigHandler() {
        propHandler = new Properties();
        initConfigFile();
    }

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
                // at WADComponent we're getting the filetype of the items by taking the last three characters
                // so this has to be more than 3 characters long
                if (nameInput.getText().length() > 3) {
                    btn_saveConfig.setEnabled(true);
                }
                else {
                    btn_saveConfig.setEnabled(false);
                }
            }
        });

        btn_removeConfig.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = configListPanel.getSelected();

                if (index != -1) {
                    loadConfigsFromXML(configFile);

                    propHandler.remove(configListPanel.getItemList().get(index).getTitle());
                    storeConfigsToXML(configFile);

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
            settingsFile = new File(homedir.concat("/.config/waddup/settings.xml"));
        }
        else if (osname.contains("Windows")) {
            configdir = new File(homedir.concat("\\AppData\\Local\\waddup"));

            if(!configdir.exists()) {
                configdir.mkdirs();
            }

            configFile = new File(homedir.concat("\\AppData\\Local\\waddup\\configs.xml"));
            settingsFile = new File(homedir.concat("\\AppData\\Local\\waddup\\settings.xml"));
        }
        else {
            configFile = new File("configs.xml");
            settingsFile = new File("settings.xml");
        }

        try {
            configFile.createNewFile();
            settingsFile.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadConfigsFromXML(File file) {
        try {
            if (file.exists()) {
                propHandler.loadFromXML(new FileInputStream(file.getAbsolutePath()));
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private void storeConfigsToXML(File file) {
        try {
            if (file.exists()) {
                propHandler.storeToXML(new FileOutputStream(file.getAbsolutePath()), "");
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public void storeSetting(String propName, String propValue) {
        loadConfigsFromXML(settingsFile);
        propHandler.setProperty(propName, propValue);
        storeConfigsToXML(settingsFile);
    }

    public String getSetting(String propName) {
        loadConfigsFromXML(settingsFile);

        if (propHandler.containsKey(propName)) {
            return propHandler.getProperty(propName);
        }
        return "";
    }

    private void storeProperty(String propName, String propValue) {
        loadConfigsFromXML(configFile);
        propHandler.setProperty(propName, propValue);
        storeConfigsToXML(configFile);
    }

    public void setConfigData(ArrayList<WADComponent> _wadList, String _iwadPath) {
        nameInput.setText("");
        btn_saveConfig.setEnabled(false);

        wadList = _wadList;
        iwadPath = _iwadPath;
    }

    public void saveConfig() throws IOException {
        ArrayList<String> wadPaths = new ArrayList<String>(wadList.stream().map(item -> item.sWADPath).collect(Collectors.toList()));
        wadPaths.add(0, iwadPath);
        String joinedWadPaths = String.join(";;", wadPaths);

        storeProperty(nameInput.getText(), joinedWadPaths);
    }

    public void loadConfig() {
        propHandler.clear();
        loadConfigsFromXML(configFile);
        Enumeration<Object> propKeys = propHandler.keys();
        configListPanel.clearItemList();
        configListPanel.setSelected(-1);

        while(propKeys.hasMoreElements()) {
            configListPanel.addItem(propKeys.nextElement().toString(), "");
        }
    }
    public ArrayList<WADComponent> getLoadedWads() {
        loadedWads.clear();
        loadConfigsFromXML(configFile);

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
