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
        sFileType = getFileType(_sWadTitle);
    }

    public void setFromFile(File file) {
        sWadTitle = file.getName();
        sWADPath = file.getAbsolutePath();
        sFileType = getFileType(file.getName());
    }

    // small function to only get the file type without the separator or anything
    // e.g. wad, pk3, deh, ...
    private String getFileType(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public String toString() {
        return sWadTitle;
    }
}