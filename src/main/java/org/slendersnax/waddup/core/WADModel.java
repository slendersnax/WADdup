package org.slendersnax.waddup.core;

public class WADModel {
    public String sWadTitle, sWADPath, sFileType;

    public WADModel(String _sWadTitle, String _sWADPath, String _sFileType) {
        sWadTitle = _sWadTitle;
        sWADPath = _sWADPath;
        sFileType = _sFileType;
    }

    public String toString() {
        return sWadTitle;
    }
}
