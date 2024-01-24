package com.slendersnax.waddup;

public class Main {
    public static void main(String[] args) {
        String sDefPath = "";
        if (args.length > 0) {
            sDefPath = args[0];
        }

        PickerWindow mainWindow = new PickerWindow(sDefPath);
    }
}