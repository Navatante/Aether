package org.jonatancarbonellmartinez.view.panels;

import org.jonatancarbonellmartinez.presenter.RegisterFlightPresenter;
import org.jonatancarbonellmartinez.view.RegisterFlightDialogView;
import org.jonatancarbonellmartinez.view.View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.Vector;

public class SessionCardPanel extends JPanel implements View {

    private RegisterFlightPresenter presenter;
    RegisterFlightDialogView registerFlightDialogView;

    private JPanel mainPanel, personMainPanel, personBoxesPanel, personAddDeletePanel, sessionMainPanel, sessionBoxesPanel, sessionAddDeletePanel;

    JComboBox personBox, sessionBox;

    JButton addPersonButton, deletePersonButton, addSessionButton, deleteSessionButton;

    ArrayDeque<JComboBox> extraPersonBoxes; // TODO the method that delete boxes from the deque, should left inside the first one. (ir borrando todas menos la primera.)
    ArrayDeque<JComboBox> extraSessionBoxes;

    public SessionCardPanel(RegisterFlightDialogView registerFlightDialogView, RegisterFlightPresenter registerFlightPresenter) {
        this.presenter = registerFlightPresenter;
        this.registerFlightDialogView = registerFlightDialogView; // this way i have access to data like pilotList or dvList, si mas adelante doy con una mejor solucion pues cambialo.
        this.initializeUI();
        setVisible(true);
    }

    @Override
    public void setupUIProperties() {
        setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        setSize(400,400);
    }

    @Override
    public void createPanels() {
        mainPanel = new JPanel(new BorderLayout());

        personMainPanel = new JPanel(new BorderLayout());
        sessionMainPanel = new JPanel(new BorderLayout());

        personBoxesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        personAddDeletePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        sessionBoxesPanel = new JPanel(new GridLayout(3,4,10,10));
        sessionAddDeletePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    }

    @Override
    public void createComponents() {
        extraPersonBoxes = new ArrayDeque<>();
        extraSessionBoxes = new ArrayDeque<>();

        personBox = View.createDynamicComboBox(new Vector<>(presenter.getAllPersons()),"PER");
        extraPersonBoxes.add(personBox);

        addPersonButton = new JButton("+");
        deletePersonButton = new JButton("-");
        addSessionButton = new JButton("+");
        deleteSessionButton = new JButton("-");

        // TODO uncomment the line below when session entity and corresponding methods are created.
        //sessionBox = View.createDynamicComboBox(new Vector<>(presenter.getAllSessions()), "Sesión");
    }

    @Override
    public void configurePanels() {
        personAddDeletePanel.setPreferredSize(new Dimension(40,100));
        sessionAddDeletePanel.setPreferredSize(new Dimension(30,100));

        personMainPanel.setPreferredSize(new Dimension(130, 100));
        sessionMainPanel.setPreferredSize(new Dimension(200, 100));


    }

    @Override
    public void configureComponents() {
        View.setInitialComboBoxLook(personBox);
        View.setPreferredSizeForComponents(CardPanel.PERSON_BOX_DIMENSION, personBox);
        //View.setPreferredSizeForComponents(CardPanel.SESSION_BOX_DIMENSION, sessionBox);
    }

    @Override
    public void assemblePanels() {
        this.add(mainPanel);
        mainPanel.add(personMainPanel, BorderLayout.WEST);
        mainPanel.add(sessionMainPanel,BorderLayout.EAST);

        personMainPanel.add(personBoxesPanel, BorderLayout.WEST);
        personMainPanel.add(personAddDeletePanel, BorderLayout.EAST);

        sessionMainPanel.add(sessionBoxesPanel, BorderLayout.WEST);
        sessionMainPanel.add(sessionAddDeletePanel, BorderLayout.EAST);


    }

    @Override
    public void assembleComponents() {
        personAddDeletePanel.add(addPersonButton);
        personAddDeletePanel.add(deletePersonButton);

        sessionAddDeletePanel.add(addSessionButton);
        sessionAddDeletePanel.add(deleteSessionButton);

        personBoxesPanel.add(personBox);

        //sessionBoxesPanel.add(sessionBox);
    }

    public void addExtraPersonBox() {
        JComboBox personBox = View.createDynamicComboBox(new Vector<>(presenter.getAllPersons()),"CREW");
        View.setInitialComboBoxLook(personBox);
        personBoxesPanel.add(personBox);
        extraPersonBoxes.add(personBox);
        // Ensure the UI updates to reflect the added component
        personBoxesPanel.revalidate();
        personBoxesPanel.repaint();
    }

    @Override
    public void addActionListeners() {

    }
}
