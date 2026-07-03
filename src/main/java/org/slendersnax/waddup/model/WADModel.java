package org.slendersnax.waddup.model;

public class WADModel {
    public String sWadTitle, sWADPath, sFileType;

    public WADModel(String _sWadTitle, String _sWADPath, String _sFileType) {
        sWadTitle = _sWadTitle;
        sWADPath = _sWADPath;
        sFileType = _sFileType;
    }

    public WADModel(String _sWadTitle, String _sWADPath) {
        sWadTitle = _sWadTitle;
        sWADPath = _sWADPath;
        sFileType = _sWadTitle.substring(_sWadTitle.length() - 3);
    }

    public String toString() {
        return sWadTitle;
    }
}
