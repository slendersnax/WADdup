package config;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
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

import wad_display.WADComponent;

public class ConfigHandler {
    private JFrame mainFrame;
    private JPanel loadedWadsPanel, btnHolderPanel, innerLoadPanel;
    private JLabel nameInstr;
    private JTextField nameInput;
    private JScrollPane loadedWadsScroller;
    private String savePanelID, loadPanelID;
    private Dimension standardBtnDim;

    public JButton btn_saveConfig, btn_loadConfig, btn_cancelSave, btn_cancelLoad;
    public JPanel savePanel, loadPanel;

    private ArrayList<WADComponent> wadList, loadedWads;
    private String iwadPath;

    public ConfigHandler(JFrame _mainFrame) {
        mainFrame = _mainFrame;

        savePanelID = "save card";
        loadPanelID = "load card";

        loadedWads = new ArrayList<WADComponent>();

        standardBtnDim = new Dimension(110, 28);

        savePanel = new JPanel();
        loadPanel = new JPanel();
        innerLoadPanel = new JPanel();
        loadedWadsPanel = new JPanel();
        btnHolderPanel = new JPanel();
        loadedWadsScroller = new JScrollPane(loadedWadsPanel);
        nameInstr = new JLabel("Enter the name of the config:");

        nameInput = new JTextField();
        btn_saveConfig = new JButton("save");
        btn_loadConfig = new JButton("load");
        btn_cancelSave = new JButton("cancel");
        btn_cancelLoad = new JButton("cancel");

        btn_loadConfig.setMaximumSize(standardBtnDim);
        btn_cancelLoad.setMaximumSize(standardBtnDim);

        loadedWadsScroller.setPreferredSize(new Dimension(480, 390));
        nameInput.setMaximumSize(new Dimension(180, 20));
        btnHolderPanel.setPreferredSize(standardBtnDim);

        savePanel.setLayout(new BoxLayout(savePanel, BoxLayout.Y_AXIS));
        loadPanel.setLayout(new BoxLayout(loadPanel, BoxLayout.Y_AXIS));
        innerLoadPanel.setLayout(new BoxLayout(innerLoadPanel, BoxLayout.LINE_AXIS));
        loadedWadsPanel.setLayout(new BoxLayout(loadedWadsPanel, BoxLayout.Y_AXIS));
        btnHolderPanel.setLayout(new BoxLayout(btnHolderPanel, BoxLayout.Y_AXIS));

        nameInstr.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_saveConfig.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_loadConfig.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_cancelSave.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_cancelLoad.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadedWadsScroller.setAlignmentX(Component.CENTER_ALIGNMENT);

        savePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        savePanel.add(Box.createVerticalGlue());
        savePanel.add(nameInstr);
        savePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        savePanel.add(nameInput);
        savePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        savePanel.add(btn_saveConfig);
        savePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        savePanel.add(btn_cancelSave);
        savePanel.add(Box.createVerticalGlue());

        btnHolderPanel.add(btn_loadConfig);
        btnHolderPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        btnHolderPanel.add(btn_cancelLoad);

        innerLoadPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        innerLoadPanel.add(loadedWadsScroller);
        innerLoadPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        innerLoadPanel.add(btnHolderPanel);
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
    }

    public void setConfigData(ArrayList<WADComponent> _wadList, String _iwadPath) {
        nameInput.setText("");
        btn_saveConfig.setEnabled(false);

        wadList = _wadList;
        iwadPath = _iwadPath;
    }

    public void saveConfig() throws IOException {
        Properties saveProps = new Properties();
        ArrayList<String> wadPaths = new ArrayList<String>(wadList.stream().map(item -> item.sWADPath).toList());
        wadPaths.add(0, iwadPath);
        String joinedWadPaths = String.join(";;", wadPaths);
        File configFile = new File("configs.xml");

        // if we don't load the existing properties we will overwrite the file contents
        if (configFile.exists()) {
            saveProps.loadFromXML(new FileInputStream("configs.xml"));
        }

        saveProps.setProperty(nameInput.getText(), joinedWadPaths);
        saveProps.storeToXML(new FileOutputStream("configs.xml"), "");
    }

    public void loadConfig() {
        btn_loadConfig.setEnabled(false);
        ArrayList<WADComponent> loadedConfigs = new ArrayList<WADComponent>();
        loadedWadsPanel.removeAll();
        final int[] nSelectedIndex = {-1};

        try {
            Properties loadProps = new Properties();

            loadProps.loadFromXML(new FileInputStream("configs.xml"));
            Enumeration<Object> propKeys = loadProps.keys();

            while(propKeys.hasMoreElements()) {
                WADComponent wadConfig = new WADComponent(propKeys.nextElement().toString(), "", "");
                loadedConfigs.add(wadConfig);
                loadedWadsPanel.add(wadConfig);
                loadedWadsPanel.revalidate();
                loadedWadsPanel.repaint();

                wadConfig.btn_select.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if(nSelectedIndex[0] != -1) {
                            loadedConfigs.get(nSelectedIndex[0]).setDeselected();
                        }

                        int newIndex = loadedConfigs.indexOf(wadConfig);

                        if (newIndex != nSelectedIndex[0]) {
                            nSelectedIndex[0] = newIndex;
                            wadConfig.setSelected();

                            String selectedWads = loadProps.getProperty(wadConfig.getTitle()).toString();
                            String[] arrSelWads = selectedWads.split(";;");

                            loadedWads.clear();
                            for(int i = 0; i < arrSelWads.length; i ++) {
                                loadedWads.add(new WADComponent(arrSelWads[i].substring(arrSelWads[i].lastIndexOf("/") + 1), arrSelWads[i], arrSelWads[i].substring(arrSelWads[i].length() - 3)));
                            }

                            btn_loadConfig.setEnabled(true);
                        }
                        else {
                            wadConfig.setDeselected();
                            nSelectedIndex[0] = -1;
                            btn_loadConfig.setEnabled(false);
                        }

                        loadedWadsPanel.revalidate();
                        loadedWadsPanel.repaint();
                    }
                });
            }
        } catch (IOException ex) {

        }
    }
    public ArrayList<WADComponent> getLoadedWads() {
        return loadedWads;
    }
}
