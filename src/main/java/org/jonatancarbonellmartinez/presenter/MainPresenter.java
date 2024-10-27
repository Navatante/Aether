package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.factory.DAOFactory;
import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.observers.Observer;
import org.jonatancarbonellmartinez.utilities.NavigationController;
import org.jonatancarbonellmartinez.view.*;

import java.awt.*;

public class MainPresenter implements Observer, Presenter { // TODO 2. after refactoring MainView, the Presenter will handle more logic than now, is it good that i have moved navigation logic to NavigationController.
    private final MainView view;
    private final  NavigationController navigationController;

    public MainPresenter(MainView view, DAOFactory daoFactory) {
        this.view = view;
        navigationController = new NavigationController(view, daoFactory);
    }

    @Override
    public void setActionListeners() {
        view.getBotonPersonal().addActionListener(e -> navigationController.navigateTo("PersonPanelView"));
        view.getAddPersonalMenuItem().addActionListener(e -> navigationController.openDialog("AddPerson"));
        view.getEditPersonalMenuItem().addActionListener(e -> navigationController.openDialog("EditPerson"));
        // Add more
    }

    @Override
    public void update() {
        if (view.getCardPanel().getComponentCount() > 0) {
            // Safe to access the first component
            Component currentComponent = view.getCardPanel().getComponent(0);
            if (currentComponent instanceof PersonPanelView) {
                PersonPanelView personPanelView = (PersonPanelView) currentComponent;
                personPanelView.updatePanel();
            } //else if (currentComponent instanceof EventPanelView) {
                //EventPanelView eventPanelView = (EventPanelView) currentComponent;
                //eventPanelView.updatePanel();
            //}
        }
    }
}
