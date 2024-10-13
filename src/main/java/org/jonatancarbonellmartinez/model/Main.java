package org.jonatancarbonellmartinez.model;

import org.jonatancarbonellmartinez.view.MainView;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;

public class Main {
    public static void main(String[] args)  {
        FlatDarkLaf.setup(); // Set de look and feel

        // CREATE A SPLASH SCREEN! SEARCH FOR JAVA SWING SPLASH SCREEN
        SwingUtilities.invokeLater(MainView::new);
    }
}
