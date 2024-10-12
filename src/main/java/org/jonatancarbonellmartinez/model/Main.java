package org.jonatancarbonellmartinez.model;

import org.jonatancarbonellmartinez.view.MainView;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;

public class Main {
    public static void main(String[] args)  {
        FlatDarkLaf.setup(); // Set de look and feel
        SwingUtilities.invokeLater(MainView::new);
    }
}
