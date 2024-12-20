package org.jonatancarbonellmartinez.utilities;


import org.jonatancarbonellmartinez.view.*;
import org.jonatancarbonellmartinez.view.RegisterFlightDialogView;

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

            case "EventPanelView":
                createAndShowEventPanelView();
                break;
            case "RecentFlightsPanelView":
                createAndShowRecentFlightsPanelView();
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
            case "AddEvent":
                new EventDialogView(mainView,false);
                break;
            case "EditEvent":
                new EventDialogView(mainView,true);
                break;
            case "RegistrarVuelo":
                new RegisterFlightDialogView(mainView);
                break;
            // Additional dialog cases
        }
    }

    private void createAndShowPersonPanelView() {
        // Check if "PersonPanelView" is already present in the CardPanel
        if (!isPanelPresent(PersonPanelView.class)) {
            PersonPanelView personPanelView = new PersonPanelView();
            mainView.getCardPanel().add(personPanelView, "PersonPanelView");
        }

        mainView.getCardLayout().show(mainView.getCardPanel(), "PersonPanelView");
        mainView.getCardPanel().revalidate();
        mainView.getCardPanel().repaint();
    }

    private void createAndShowEventPanelView() {
        // Check if "EventPanelView" is already present in the CardPanel
        if (!isPanelPresent(EventPanelView.class)) {
            EventPanelView eventPanelView = new EventPanelView();
            mainView.getCardPanel().add(eventPanelView, "EventPanelView");
        }

        mainView.getCardLayout().show(mainView.getCardPanel(), "EventPanelView");
        mainView.getCardPanel().revalidate();
        mainView.getCardPanel().repaint();
    }

    private void createAndShowRecentFlightsPanelView() {
        // Check if "RecentFlightsPanelView" is already present in the CardPanel
        if (!isPanelPresent(RecentFlightsPanelView.class)) {
            RecentFlightsPanelView recentFlightsPanelView = new RecentFlightsPanelView();
            mainView.getCardPanel().add(recentFlightsPanelView, "RecentFlightsPanelView");
        }

        mainView.getCardLayout().show(mainView.getCardPanel(), "RecentFlightsPanelView");
        mainView.getCardPanel().revalidate();
        mainView.getCardPanel().repaint();
    }

    // Helper method to check if a specific panel is present
    private boolean isPanelPresent(Class<?> panelClass) {
        for (int i = 0; i < mainView.getCardPanel().getComponentCount(); i++) {
            if (panelClass.isInstance(mainView.getCardPanel().getComponent(i))) {
                return true;
            }
        }
        return false;
    }


}

