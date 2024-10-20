package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.presenter.AddPersonPresenter;
import javax.swing.*;
import java.awt.*;

public class AddPersonView extends JDialog {
    private MainView mainView;
    private AddPersonPresenter presenter;

    public AddPersonView(MainView mainView, PersonDAO personDAO) {
        super(mainView,"Añadir personal",true);
        this.mainView = mainView; // This is mainly used to do things like setLocationRelativeTo(mainView);
        this.presenter = new AddPersonPresenter(this, personDAO);
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setResizable(false);
        setSize(914, 360);
        setLocationRelativeTo(mainView);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
