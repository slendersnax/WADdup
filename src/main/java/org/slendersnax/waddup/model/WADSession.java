package org.slendersnax.waddup.model;

import java.util.ArrayList;

public class WADSession {
    private WADModel iWAD;
    private ArrayList<WADModel> pWADs;

    public WADSession() {
        pWADs = new ArrayList<>();
    }

    public WADModel getiWAD() {
        return iWAD;
    }

    public void setiWAD(WADModel iWAD) {
        this.iWAD = iWAD;
    }

    public ArrayList<WADModel> getpWADs() {
        return pWADs;
    }

    public void addPWAD(WADModel pWAD) {
        pWADs.add(pWAD);
    }

    public void setpWADs(ArrayList<WADModel> wads) {
        pWADs = new ArrayList<>(wads);
    }

    public void removePWADByPath(String pWADPath) {
        pWADs.removeIf(pWAD -> pWAD.sWADPath.equals(pWADPath));
    }

    public void resetSession() {
        this.iWAD = null;
        this.pWADs.clear();
    }
}
