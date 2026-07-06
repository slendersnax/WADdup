package org.slendersnax.waddup;

import org.slendersnax.waddup.infrastructure.PropWrapper;
import org.slendersnax.waddup.ui.AppWindow;

public class Main {
    public static void main(String[] args) {
        PropWrapper propWrapper = new PropWrapper();

        AppWindow mainWindow = new AppWindow(propWrapper);
    }
}