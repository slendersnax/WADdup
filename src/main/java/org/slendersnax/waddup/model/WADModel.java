package org.slendersnax.waddup.model;

import org.slendersnax.waddup.infrastructure.Platform;

import java.io.File;

public class WADModel {
    private String sWadName;
    private String sWADPath;
    private String sFileType;

    public WADModel(WADModel wadModel) {
        this.sWadName = wadModel.getWadName();
        this.sWADPath = wadModel.getWADPath();
        this.sFileType = wadModel.getFileType();
    }

    public WADModel(String _sWadPath) {
        String[] sepPath = _sWadPath.split(Platform.getFileSep());

        sWadName = sepPath[sepPath.length - 1];
        sWADPath = _sWadPath;
        sFileType = getFileType(sWadName);
    }

    public WADModel(String _sWadName, String _sWADPath) {
        sWadName = _sWadName;
        sWADPath = _sWADPath;
        sFileType = getFileType(_sWadName);
    }

    public WADModel(File file) {
        sWadName = file.getName();
        sWADPath = file.getAbsolutePath();
        sFileType = getFileType(file.getName());
    }

    public String getFileType() {
        return sFileType;
    }

    public String getWADPath() {
        return sWADPath;
    }

    public String getWadName() {
        return sWadName;
    }

    // small function to only get the file type without the separator or anything
    // e.g. wad, pk3, deh, ...
    private String getFileType(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public String toString() {
        return sWadName;
    }
}