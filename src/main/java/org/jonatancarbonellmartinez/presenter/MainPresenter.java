package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.observers.Observer;
import org.jonatancarbonellmartinez.utilities.NavigationController;
import org.jonatancarbonellmartinez.view.*;

import java.awt.*;

public class MainPresenter implements Observer, Presenter {
    private final MainView view;
    private final  NavigationController navigationController;

    public MainPresenter(MainView view) {
        this.view = view;
        navigationController = new NavigationController(view);
    }

    @Override
    public void setActionListeners() {
        view.getBotonPersonal().addActionListener(e -> navigationController.navigateTo("PersonPanelView"));
        view.getAnadirPersonalMenuItem().addActionListener(e -> navigationController.openDialog("AddPerson"));
        view.getEditarPersonalMenuItem().addActionListener(e -> navigationController.openDialog("EditPerson"));
        view.getAnadirEventoMenuItem().addActionListener(e -> navigationController.openDialog("AddEvent"));
        view.getEditarEventoMenuItem().addActionListener(e -> navigationController.openDialog("EditEvent"));
        // Add more
    }

    @Override
    public void update() {
        if (view.getCardPanel().getComponentCount() > 0) {
            // Safe to access the first component
            Component currentComponent = view.getCardPanel().getComponent(0);
            String currentComponentName = currentComponent.getClass().getSimpleName();

            switch (currentComponentName) {
                case "PersonPanelView":
                    ((PersonPanelView) currentComponent).updatePanel();
                    break;
                //case "EventPanelView":
                    //((EventPanelView) currentComponent).updatePanel();
                    //break;
                // Add more cases if needed
            }
        }
    }

}
