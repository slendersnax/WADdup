package org.slendersnax.waddup;

import org.slendersnax.waddup.exception.RepositoryException;
import org.slendersnax.waddup.model.ApplicationContext;
import org.slendersnax.waddup.ui.AppWindow;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        try {
            ApplicationContext applicationContext = new ApplicationContext();

            AppWindow mainWindow = new AppWindow(applicationContext);
        } catch (RepositoryException re) {
            JOptionPane.showMessageDialog(
                    null,
                    re.getMessage(),
                    "Startup Error",
                    JOptionPane.ERROR_MESSAGE
            );

            System.exit(1);
        }
    }
}