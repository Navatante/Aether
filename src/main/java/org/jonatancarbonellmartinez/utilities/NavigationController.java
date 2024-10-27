package org.jonatancarbonellmartinez.utilities;

import org.jonatancarbonellmartinez.view.MainView;
import org.jonatancarbonellmartinez.view.PersonDialogView;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeSupport;

public class NavigationController { // TODO i don't know if this would fit good my app. probably delete entire class and forget it. --------- Controlalo desde MainPesenter, creo que lo puedo integrar sin problemas.
    private CardLayout cardLayout;
    private JPanel cardPanel;

    // Initialize views
    private MainView mainView;
    private PersonDialogView personDialogView;

    public NavigationController(MainView mainView, JPanel cardPanel, CardLayout cardLayout) {
        this.mainView = mainView;
        this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;
    }

    public void navigateTo(String view) { // Used to switch cards
        switch (view) {
            case "MainView":
                cardLayout.show(cardPanel, "MainView");
                break;
            case "PersonDialogView":
                if (personDialogView == null) {
                    //personDialogView = new PersonDialogView(mainView, /* other params */);
                    cardPanel.add(personDialogView, "PersonDialogView");
                }
                cardLayout.show(cardPanel, "PersonDialogView");
                break;
            // Add more views as necessary
        }
    }

    public void openDialog(String dialogType) { // Used to open dialogs
        switch (dialogType) {
            case "AddPerson":
                //new PersonDialogView(mainView, /* other params */).setVisible(true);
                break;
            // Additional dialog cases
        }
    }
}

