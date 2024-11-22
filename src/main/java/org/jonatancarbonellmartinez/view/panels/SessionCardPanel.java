package org.jonatancarbonellmartinez.view.panels;

import org.jonatancarbonellmartinez.presenter.RegisterFlightPresenter;
import org.jonatancarbonellmartinez.view.RegisterFlightDialogView;
import org.jonatancarbonellmartinez.view.View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.Vector;

public class SessionCardPanel extends JPanel implements View {

    private RegisterFlightPresenter presenter;
    private RegisterFlightDialogView registerFlightDialogView;

    private JPanel mainPanel, personMainPanel, personBoxesPanel, personAddDeletePanel, sessionMainPanel, sessionBoxesPanel, sessionAddDeletePanel;
    private JScrollPane personScrollPane;

    private JComboBox personBox, sessionBox;

    private JButton addPersonButton;

    private JButton deletePersonButton;
    private JButton addSessionButton;
    private JButton deleteSessionButton;

    JPopupMenu personPopupMenu; // TODO quitar los botones de + y - y meter el menu contextual.

    JMenuItem addPersonItem, deletePersonItem; // TODO quitar los botones de + y - y meter el menu contextual.

    private ArrayDeque<JComboBox> extraPersonBoxesDeque; // TODO the method that delete boxes from the deque, should left inside the first one. (ir borrando todas menos la primera.)
    private ArrayDeque<JComboBox> extraSessionBoxesDeque;
    private ArrayDeque<Box.Filler> extraPersonBoxFillersDeque;

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

        personBoxesPanel = new JPanel();
        personBoxesPanel.setLayout(new BoxLayout(personBoxesPanel, BoxLayout.Y_AXIS));

        personScrollPane = new JScrollPane(personBoxesPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        personAddDeletePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        sessionBoxesPanel = new JPanel(new GridLayout(3,4,10,10));
        sessionAddDeletePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    }

    @Override
    public void createComponents() {
        extraPersonBoxesDeque = new ArrayDeque<>();
        extraSessionBoxesDeque = new ArrayDeque<>();
        extraPersonBoxFillersDeque = new ArrayDeque<>();

        personBox = View.createDynamicComboBox(new Vector<>(presenter.getAllPersonsVector()),"PER");
        extraPersonBoxesDeque.add(personBox);

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
        personScrollPane.setPreferredSize(new Dimension(100, 100));

        personAddDeletePanel.setBorder(new EmptyBorder(0,15,0,0));
    }

    @Override
    public void configureComponents() {
        View.setInitialComboBoxLook(personBox);

        personBox.setMaximumSize(CardPanel.PERSON_BOX_DIMENSION);
        personBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        //View.setPreferredSizeForComponents(CardPanel.SESSION_BOX_DIMENSION, sessionBox);
    }

    @Override
    public void assemblePanels() {
        this.add(mainPanel);
        mainPanel.add(personMainPanel, BorderLayout.WEST);
        mainPanel.add(sessionMainPanel,BorderLayout.EAST);

        personScrollPane.setViewportView(personBoxesPanel);
        personMainPanel.add(personScrollPane, BorderLayout.WEST);
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

        personBoxesPanel.add(new Box.Filler(CardPanel.SPACE_MIN_SIZE, CardPanel.SPACE_PREF_SIZE,CardPanel.SPACE_MAX_SIZE));
        personBoxesPanel.add(personBox);
        personBoxesPanel.add(new Box.Filler(CardPanel.SPACE_MIN_SIZE, CardPanel.SPACE_PREF_SIZE,CardPanel.SPACE_MAX_SIZE));

        //sessionBoxesPanel.add(sessionBox);
    }

    public void addExtraPersonBox() {
        if(extraPersonBoxesDeque.size()<10) { // I limit the addition of personBoxes to 10
            JComboBox personBox = View.createDynamicComboBox(new Vector<>(presenter.getAllPersonsVector()),"PER");
            View.setInitialComboBoxLook(personBox);
            personBox.setMaximumSize(CardPanel.PERSON_BOX_DIMENSION);
            personBox.setAlignmentX(Component.CENTER_ALIGNMENT);
            personBoxesPanel.add(personBox);
            extraPersonBoxesDeque.add(personBox);
            Box.Filler boxFiller = new Box.Filler(CardPanel.SPACE_MIN_SIZE, CardPanel.SPACE_PREF_SIZE,CardPanel.SPACE_MAX_SIZE);
            personBoxesPanel.add(boxFiller);
            extraPersonBoxFillersDeque.add(boxFiller);
            // Ensure the UI updates to reflect the added component
            personBoxesPanel.revalidate();
            personBoxesPanel.repaint();
        }
    }

    public void deleteExtraPersonBox() {
        // Ensure there are more than one item in the deque
        if (extraPersonBoxesDeque.size() > 1) {
            // Get and remove the last JComboBox added (but not the first one)
            JComboBox lastPersonBox = extraPersonBoxesDeque.removeLast();

            // Remove the JComboBox from the panel
            personBoxesPanel.remove(lastPersonBox);

            // If there are fillers associated, remove the last filler
            if (!extraPersonBoxFillersDeque.isEmpty()) {
                Box.Filler lastFiller = extraPersonBoxFillersDeque.removeLast();
                personBoxesPanel.remove(lastFiller);
            }

            // Revalidate and repaint to reflect the changes
            personBoxesPanel.revalidate();
            personBoxesPanel.repaint();
        }
    }


    @Override
    public void addActionListeners() {

    }

    // Getters
    public JButton getAddPersonButton() {
        return addPersonButton;
    }

    public JButton getDeletePersonButton() {
        return deletePersonButton;
    }
}
