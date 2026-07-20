package org.slendersnax.waddup.ui;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.slendersnax.waddup.exception.InvalidConfigException;
import org.slendersnax.waddup.exception.NoSelectionException;
import org.slendersnax.waddup.model.WADModel;
import org.slendersnax.waddup.model.Config;
import org.slendersnax.waddup.model.WADSession;
import org.slendersnax.waddup.repository.ConfigRepository;
import org.slendersnax.waddup.ui.components.ItemPanel;
import org.slendersnax.waddup.ui.components.VerticalBtnPanel;

public class LoadConfigPanel extends JPanel {

    private final VerticalBtnPanel panelBtnContainer;
    private final ItemPanel<Config> panelConfigs;
    private final JPanel panelInnerContainer;
    private final JButton btn_loadConfig, btn_cancelLoad, btn_removeConfig;
    private final JLabel lbl_loadTitle;
    private final WADSession wadSession;

    public LoadConfigPanel(Dimension frameSize, ConfigRepository configRepository, ArrayList<Config> configs, WADSession wadSession) {
        this.wadSession = wadSession;

        panelConfigs = new ItemPanel<Config>(new Dimension((int)(frameSize.width * 0.85), frameSize.height), configs, () -> configRepository.save(configs), true);
        panelBtnContainer = new VerticalBtnPanel(new Dimension((int)(frameSize.width * 0.20), frameSize.height));
        panelInnerContainer = new JPanel();

        lbl_loadTitle = new JLabel("load config");
        btn_loadConfig = new JButton("load");
        btn_removeConfig = new JButton("remove");
        btn_cancelLoad = new JButton("cancel");

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

    public void refreshItemPanel() {
        panelConfigs.rebuildItems();
    }

    public void onLoadConfigRequested(ActionListener listener) {
        btn_loadConfig.addActionListener(listener);
    }

    public void onCancelLoadRequested(ActionListener listener) {
        btn_cancelLoad.addActionListener(listener);
    }

    public void addBtnActions() {
        btn_removeConfig.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panelConfigs.removeSelectedItems();
            }
        });
    }

    public void loadSelectedConfig() throws NoSelectionException, InvalidConfigException {
        try {
            ArrayList<Config> selectedConfig = panelConfigs.getSelected();

            if (selectedConfig.size() > 1) {
                throw new InvalidConfigException("More than one config selected");
            }

            wadSession.resetSession();

            wadSession.setiWAD(selectedConfig.get(0).getWadSession().getiWAD());

            for (WADModel wadModel : selectedConfig.get(0).getWadSession().getpWADs()) {
                wadSession.getpWADs().add(new WADModel(wadModel));
            }
        }
        catch(NoSelectionException ex) {
            throw ex;
        }
    }
}
