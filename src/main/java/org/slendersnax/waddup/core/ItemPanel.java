package org.slendersnax.waddup.core;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

public class ItemPanel extends JPanel {
    private ArrayList<WADComponent> itemList;
    private JPanel itemContainer;
    private JScrollPane containerScroller;
    private int nSelectedIndex;
    public ItemPanel(Dimension size) {
        setSize(size);
        setMaximumSize(size);
        setPreferredSize(size);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        itemList = new ArrayList<WADComponent>();

        itemContainer = new JPanel();
        itemContainer.setLayout(new BoxLayout(itemContainer, BoxLayout.PAGE_AXIS));

        containerScroller = new JScrollPane(itemContainer);
        containerScroller.getVerticalScrollBar().setUnitIncrement(16);

        containerScroller.setSize(size);
        containerScroller.setMaximumSize(size);
        containerScroller.setPreferredSize(size);

        nSelectedIndex = -1;

        add(containerScroller);
    }

    public ArrayList<WADComponent> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<WADComponent> _itemList) {
        itemList.clear();
        itemContainer.removeAll();

        for(int i = 0; i < _itemList.size(); i ++) {
            addItem(_itemList.get(i).getTitle(), _itemList.get(i).sWADPath);
        }
    }

    public void clearItemList() {
        itemList.clear();
        itemContainer.removeAll();

        revalidate();
        repaint();
    }

    public void removeItem(int index) {
        WADComponent wadToRemove = itemList.get(index);

        itemContainer.remove(wadToRemove);
        itemList.remove(index);

        nSelectedIndex = -1;

        revalidate();
        repaint();
    }

    public void moveItemBack(int index) {
        WADComponent toMove = itemList.get(index);

        Collections.swap(itemList, index, index - 1);
        itemContainer.remove(toMove);
        itemContainer.add(toMove, index - 1);

        revalidate();
        repaint();
    }

    public void moveItemForward(int index) {
        WADComponent toMove = itemList.get(nSelectedIndex);

        Collections.swap(itemList, nSelectedIndex, nSelectedIndex + 1);
        itemContainer.remove(toMove);
        itemContainer.add(toMove, nSelectedIndex + 1);

        revalidate();
        repaint();
    }

    public void addItem(String name, String path) {
        WADComponent wad = new WADComponent(name, path, name.substring(name.length() - 3));

        itemList.add(wad);
        itemContainer.add(wad);

        wad.btn_select.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // deselecting old wad if it exists
                if (nSelectedIndex != -1) {
                    itemList.get(nSelectedIndex).setDeselected();
                }

                int newIndex = itemList.indexOf(wad);

                // we only select the new one if it isn't the same as the old one
                if (nSelectedIndex != newIndex) {
                    wad.setSelected();
                    nSelectedIndex = itemList.indexOf(wad);
                }
                // otherwise we deselect it
                else {
                    wad.setDeselected();
                    nSelectedIndex = -1;
                }

                revalidate();
                repaint();
            }
        });

        revalidate();
        repaint();
    }

    public int getSelected() {
        return nSelectedIndex;
    }

    public void setSelected(int index) {
        nSelectedIndex = index;
    }
}
