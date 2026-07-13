package org.slendersnax.waddup.model;

import java.io.File;

public class WADModel {
    public String sWadTitle, sWADPath, sFileType;

    public WADModel() {

    }

    public WADModel(String _sWadTitle, String _sWADPath) {
        setFromData(_sWadTitle, _sWADPath);
    }

    public WADModel(File file) {
        setFromFile(file);
    }

    public void setFromData(String _sWadTitle, String _sWADPath) {
        sWadTitle = _sWadTitle;
        sWADPath = _sWADPath;
        sFileType = _sWadTitle.substring(_sWadTitle.length() - 3);
    }

    public void setFromFile(File file) {
        sWadTitle = file.getName();
        sWADPath = file.getAbsolutePath();
        sFileType = file.getName().substring(file.getName().length() - 3);
    }

    public String toString() {
        return sWadTitle;
    }
}
