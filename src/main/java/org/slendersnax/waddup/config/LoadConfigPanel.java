package org.slendersnax.waddup.config;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import org.slendersnax.waddup.core.VerticalBtnPanel;
import org.slendersnax.waddup.core.ItemPanel;
import org.slendersnax.waddup.core.PropWrapper;
import org.slendersnax.waddup.core.WADComponent;
import org.slendersnax.waddup.core.SlenderConstants;

public class LoadConfigPanel extends JPanel {

    private final VerticalBtnPanel panelBtnContainer;
    private final ItemPanel panelConfigs;
    private final JPanel panelInnerContainer;
    private final JButton btn_loadConfig, btn_cancelLoad, btn_removeConfig;
    private final JLabel lbl_loadTitle;
    private final PropWrapper propWrapper;

    public LoadConfigPanel(Dimension frameSize) {
        panelConfigs = new ItemPanel(new Dimension((int)(frameSize.width * 0.85), frameSize.height));
        panelBtnContainer = new VerticalBtnPanel(new Dimension((int)(frameSize.width * 0.20), frameSize.height));
        panelInnerContainer = new JPanel();

        lbl_loadTitle = new JLabel("load config");
        btn_loadConfig = new JButton("load");
        btn_removeConfig = new JButton("remove");
        btn_cancelLoad = new JButton("cancel");

        propWrapper = new PropWrapper();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        panelInnerContainer.setLayout(new BoxLayout(panelInnerContainer, BoxLayout.LINE_AXIS));

        panelBtnContainer.add(Box.createVerticalGlue());
        panelBtnContainer.addElem(btn_loadConfig);
        panelBtnContainer.addElem(btn_removeConfig);
        panelBtnContainer.addElem(btn_cancelLoad);
        panelBtnContainer.add(Box.createVerticalGlue());

        panelInnerContainer.add(Box.createRigidArea(new Dimension(5, 0)));
        panelInnerContainer.add(panelConfigs);
        panelInnerContainer.add(Box.createRigidArea(new Dimension(5, 0)));
        panelInnerContainer.add(panelBtnContainer);

        add(Box.createRigidArea(new Dimension(0, 5)));
        add(lbl_loadTitle);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(panelInnerContainer);

        addBtnActions();
    }

    public JButton getBtn_loadConfig() {
        return btn_loadConfig;
    }

    public JButton getBtn_cancelLoad() {
        return btn_cancelLoad;
    }

    public void addBtnActions() {
        btn_removeConfig.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = panelConfigs.getSelected();

                if (index != -1) {
                    propWrapper.removeProperty(PropWrapper.FILE_CONFIG_INDEX, panelConfigs.getItemList().get(index).getTitle());
                    panelConfigs.removeSelectedItem();
                }
            }
        });
    }
    // TODO: optimise the loadconfig process a bit
    // currently we're clearing and reloading all the configs everytime "load config" is pressed
    // not a super high priority
    public void loadConfig() {
        Enumeration<Object> propKeys = propWrapper.getKeys(PropWrapper.FILE_CONFIG_INDEX);
        panelConfigs.clearItemList();
        panelConfigs.resetSelected();

        while(propKeys.hasMoreElements()) {
            panelConfigs.addItem(propKeys.nextElement().toString(), "");
        }
    }
    public ArrayList<WADComponent> getLoadedWads() {
        ArrayList<WADComponent> loadedWads = new ArrayList<WADComponent>();

        int selectedConfig = panelConfigs.getSelected();

        if (selectedConfig != -1) {
            String selConfig = panelConfigs.getItemList().get(panelConfigs.getSelected()).getTitle();
            String selectedWads = propWrapper.getProperty(PropWrapper.FILE_CONFIG_INDEX, selConfig);
            String[] arrSelWads = selectedWads.split(SlenderConstants.CONFIG_ITEM_SEPARATOR);

            for (String arrSelWad : arrSelWads) {
                loadedWads.add(new WADComponent(arrSelWad.substring(arrSelWad.lastIndexOf("/") + 1), arrSelWad, arrSelWad.substring(arrSelWad.length() - 3)));
            }
        }

        return loadedWads;
    }
}
