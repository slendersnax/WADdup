package org.slendersnax.waddup.core;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.Dimension;

public class ItemPanel<T> extends JPanel {
    private final ArrayList<T> itemList;
    private final JList<String> objJList;
    private final DefaultListModel<String> listModel;

    public ItemPanel(Dimension size, boolean enableMultiSelection) {
        setSize(size);
        setMaximumSize(size);
        setPreferredSize(size);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        itemList = new ArrayList<T>();

        listModel = new DefaultListModel<String>();
        objJList = new JList<String>();

        objJList.setModel(listModel);

        if (!enableMultiSelection) {
            objJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
        else {
            objJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        }

        JScrollPane containerScroller = new JScrollPane(objJList);
        containerScroller.getVerticalScrollBar().setUnitIncrement(16);

        containerScroller.setSize(size);
        containerScroller.setMaximumSize(size);
        containerScroller.setPreferredSize(size);

        add(containerScroller);
    }

    public ArrayList<T> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<T> _itemList) {
        itemList.clear();
        listModel.clear();

        for (T obj : _itemList) {
            addItem(obj);
        }
    }

    public void clearItemList() {
        itemList.clear();
        listModel.clear();

        revalidate();
        repaint();
    }

    public void removeSelectedItems() {
        while (!objJList.isSelectionEmpty()) {
            int nSelectedIndex = objJList.getSelectedIndex();

            itemList.remove(nSelectedIndex);
            listModel.remove(nSelectedIndex);

            revalidate();
            repaint();
        }
    }

    public void moveSelectedItemBack() {
        if (!objJList.isSelectionEmpty()) {
            int[] selectedIndices = objJList.getSelectedIndices();

            for(int i = 0; i < selectedIndices.length; i ++) {
                if (selectedIndices[i] > 0 && !objJList.isSelectedIndex(selectedIndices[i] - 1)) {
                    Collections.swap(itemList, selectedIndices[i], selectedIndices[i] - 1);

                    String aux = listModel.get(selectedIndices[i]);
                    listModel.remove(selectedIndices[i]);
                    listModel.add(selectedIndices[i] - 1, aux);
                    selectedIndices[i] --;
                }
            }

            // need to do it this way, the setSelectedIndex deselects previously selected items
            // so when multiple are selected, only the last one stays selected after moving
            objJList.setSelectedIndices(selectedIndices);

            revalidate();
            repaint();
        }
    }

    public void moveSelectedItemForward() {
        if (!objJList.isSelectionEmpty()) {
            int[] selectedIndices = objJList.getSelectedIndices();

            for(int i = selectedIndices.length - 1; i >= 0; i --) {
                if (selectedIndices[i] < itemList.size() - 1 && !objJList.isSelectedIndex(selectedIndices[i] + 1)) {
                    Collections.swap(itemList, selectedIndices[i], selectedIndices[i] + 1);

                    String aux = listModel.get(selectedIndices[i]);
                    listModel.remove(selectedIndices[i]);
                    listModel.add(selectedIndices[i] + 1, aux);
                    selectedIndices[i] ++;
                }
            }

            objJList.setSelectedIndices(selectedIndices);

            revalidate();
            repaint();
        }
    }

    public void addItem(T obj) {
        itemList.add(obj);
        listModel.addElement(obj.toString());

        revalidate();
        repaint();
    }

    public ArrayList<T> getSelected() {
        ArrayList<T> selectedObjects = new ArrayList<T>();
        int[] selectedIndices = objJList.getSelectedIndices();

        for(int index : selectedIndices) {
            selectedObjects.add(itemList.get(index));
        }

        return selectedObjects;
    }
}
