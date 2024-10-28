package org.jonatancarbonellmartinez.utilities;


import org.jonatancarbonellmartinez.view.MainView;
import org.jonatancarbonellmartinez.view.PersonDialogView;
import org.jonatancarbonellmartinez.view.PersonPanelView;

public class NavigationController {

    private int componentCount;
    private MainView mainView;

    public NavigationController(MainView mainView) {
        this.mainView = mainView;
    }

    public void navigateTo(String view) {

        componentCount = mainView.getCardPanel().getComponentCount(); // Check if there are any components in the card panel

        switch (view) {
            case "PersonPanelView":
                createAndShowPersonPanelView();
                break;

            case "Test":
                break;
            // Add more cases for other views as necessary
        }
    }

    public void openDialog(String dialogType) {

        switch (dialogType) {
            case "AddPerson":
                new PersonDialogView(mainView,false);
                break;
            case "EditPerson":
                new PersonDialogView(mainView,true);
                break;
            // Additional dialog cases
        }
    }

    private void createAndShowPersonPanelView() {
        boolean isPersonViewVisible = componentCount > 0 && mainView.getCardPanel().getComponent(0) instanceof PersonPanelView;
        if (!isPersonViewVisible) {
            PersonPanelView personPanelView = new PersonPanelView();
            mainView.getCardPanel().add(personPanelView, "PersonPanelView");
        }
        mainView.getCardLayout().show(mainView.getCardPanel(), "PersonPanelView");
        // Refresh the panel
        mainView.getCardPanel().revalidate();
        mainView.getCardPanel().repaint();
    }

}

