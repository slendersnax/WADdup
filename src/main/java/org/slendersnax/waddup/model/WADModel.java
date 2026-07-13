package org.slendersnax.waddup.model;

import java.io.File;

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

    public WADModel(File file) {
        sWadTitle = file.getName();
        sWADPath = file.getAbsolutePath();
        sFileType = file.getName().substring(file.getName().length() - 3);
    }

    public String toString() {
        return sWadTitle;
    }
}
