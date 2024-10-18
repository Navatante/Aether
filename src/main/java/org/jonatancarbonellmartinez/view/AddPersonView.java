package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.presenter.AddPersonPresenter;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddPersonView extends JFrame {
    private AddPersonPresenter presenter;

    // Set the presenter
    public void setPresenter(AddPersonPresenter presenter) {
        this.presenter = presenter;
        initializeUI();
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
