package org.slendersnax.waddup.model;

import java.util.ArrayList;
import org.slendersnax.waddup.exception.InvalidSessionException;

public class WADSession {
    private WADModel iWAD;
    private final ArrayList<WADModel> pWADs;

    public WADSession() {
        pWADs = new ArrayList<>();
    }

    public WADSession(WADSession wadSession) {
        pWADs = new ArrayList<>();

        iWAD = new WADModel(wadSession.getiWAD());

        for (WADModel wadModel : wadSession.getpWADs()) {
            pWADs.add(new WADModel(wadModel));
        }
    }

    public WADSession(WADModel iWAD, ArrayList<WADModel> pWADs) {
        this.pWADs = new ArrayList<>();

        this.iWAD = new WADModel(iWAD);

        for (WADModel wadModel : pWADs) {
            this.pWADs.add(new WADModel(wadModel));
        }
    }

    public WADModel getiWAD() {
        return iWAD;
    }

    public void setiWAD(WADModel wadModel) {
        this.iWAD = new WADModel(wadModel);
    }

    public ArrayList<WADModel> getpWADs() {
        return pWADs;
    }

    public void resetSession() {
        this.iWAD = null;
        this.pWADs.clear();
    }

    public void validateSession() throws InvalidSessionException {
        if (this.iWAD == null) {
            throw new InvalidSessionException("No IWAD selected");
        }
    }

    @Override
    public String toString() {
        ArrayList<String> wadNames = new ArrayList<>();

        wadNames.add(this.iWAD.toString());

        for (WADModel wadModel : this.pWADs) {
            wadNames.add(wadModel.toString());
        }

        return String.join(", ", wadNames);
    }
}
