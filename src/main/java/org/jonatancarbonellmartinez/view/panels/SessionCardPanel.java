package org.jonatancarbonellmartinez.view.panels;

import org.jonatancarbonellmartinez.viewmodel.RegisterFlightPresenter;
import org.jonatancarbonellmartinez.view.RegisterFlightDialogView;
import org.jonatancarbonellmartinez.view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;
import java.util.Vector;

public class SessionCardPanel extends JPanel implements View {

    private RegisterFlightPresenter presenter;
    private RegisterFlightDialogView registerFlightDialogView;

    private JPanel mainPanel, personMainPanel, personBoxesPanel, sessionMainPanel, sessionBoxesPanel;
    private JScrollPane personScrollPanel, sessionScrollPanel;

    private JComboBox personBox, sessionBox;

    private ArrayDeque<JComboBox> extraPersonBoxesDeque;
    private ArrayDeque<JComboBox> extraSessionBoxesDeque;
    private ArrayDeque<Box.Filler> extraPersonBoxFillersDeque;
    private ArrayDeque<Box.Filler> extraSessionBoxFillersDeque;

    JPopupMenu sessionPopupMenu;
    JMenuItem addGroupItem, deleteGroupItem, addPersonItem, deletePersonItem, addSessionItem, deleteSessionItem;

    public SessionCardPanel(RegisterFlightDialogView registerFlightDialogView, RegisterFlightPresenter registerFlightPresenter) {
        this.presenter = registerFlightPresenter;
        this.registerFlightDialogView = registerFlightDialogView; // this way i have access to data like pilotList or dvList, si mas adelante doy con una mejor solucion pues cambialo.
        this.initializeUI();
        setVisible(true);
    }

    @Override
    public void setupUIProperties() {
        setLayout(new FlowLayout(FlowLayout.LEFT,10,12));
        setSize(400,400);
    }

    @Override
    public void createPanels() {
        mainPanel = new JPanel(new BorderLayout());

        personMainPanel = new JPanel(new BorderLayout());
        sessionMainPanel = new JPanel(new BorderLayout());

        personBoxesPanel = new JPanel();
        personBoxesPanel.setLayout(new BoxLayout(personBoxesPanel, BoxLayout.Y_AXIS));

        personScrollPanel = new JScrollPane(personBoxesPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        sessionBoxesPanel = new JPanel();
        sessionBoxesPanel.setLayout(new BoxLayout(sessionBoxesPanel, BoxLayout.Y_AXIS));

        sessionScrollPanel = new JScrollPane(sessionBoxesPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    }

    @Override
    public void createComponents() {
        extraPersonBoxesDeque = new ArrayDeque<>();
        extraSessionBoxesDeque = new ArrayDeque<>();
        extraPersonBoxFillersDeque = new ArrayDeque<>();
        extraSessionBoxFillersDeque = new ArrayDeque<>();

        personBox = View.createDynamicComboBox(new Vector<>(presenter.getAllPersonsVector()),"CREW");
        extraPersonBoxesDeque.add(personBox);

        sessionBox = View.createDynamicComboBox(new Vector<>(presenter.getAllSessionsVector()), "Papeleta");
        extraSessionBoxesDeque.add(sessionBox);

        sessionPopupMenu = new JPopupMenu();

        addGroupItem = new JMenuItem("Añadir grupo");
        deleteGroupItem = new JMenuItem("Eliminar grupo");
        addPersonItem = new JMenuItem("Añadir persona");
        deletePersonItem = new JMenuItem("Eliminar persona");
        addSessionItem = new JMenuItem("Añadir papeleta");
        deleteSessionItem = new JMenuItem("Eliminar papeleta");
    }

    @Override
    public void configurePanels() {
        personMainPanel.setPreferredSize(new Dimension(170, 100));
        personScrollPanel.setPreferredSize(new Dimension(120, 100));
        sessionMainPanel.setPreferredSize(new Dimension(200, 100)); // TODO adapt sizes
        sessionScrollPanel.setPreferredSize(new Dimension(170, 100)); // TODO adapt sizes

        // Añadir un MouseListener al panel para detectar clic derecho
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) { // Verificar si es un clic derecho
                    sessionPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) { // Verificar si es un clic derecho
                    sessionPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    @Override
    public void configureComponents() {
        View.setInitialComboBoxLook(personBox);
        View.setInitialComboBoxLook(sessionBox);

        personBox.setMaximumSize(CrewCardPanel.PERSON_BOX_DIMENSION);
        personBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        sessionBox.setMaximumSize(CrewCardPanel.SESSION_BOX_DIMENSION);
        sessionBox.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    @Override
    public void assemblePanels() {
        this.add(mainPanel);
        mainPanel.add(personMainPanel, BorderLayout.WEST);
        mainPanel.add(sessionMainPanel,BorderLayout.EAST);

        personScrollPanel.setViewportView(personBoxesPanel);
        personMainPanel.add(personScrollPanel, BorderLayout.WEST); // TODO maybe a borderlayour is not necessary

        sessionScrollPanel.setViewportView(sessionBoxesPanel);
        sessionMainPanel.add(sessionScrollPanel, BorderLayout.WEST); // TODO maybe a borderlayour is not necessary

        sessionPopupMenu.add(addGroupItem);
        sessionPopupMenu.add(deleteGroupItem);
        sessionPopupMenu.addSeparator();
        sessionPopupMenu.add(addPersonItem);
        sessionPopupMenu.add(deletePersonItem);
        sessionPopupMenu.addSeparator();
        sessionPopupMenu.add(addSessionItem);
        sessionPopupMenu.add(deleteSessionItem);
    }

    @Override
    public void assembleComponents() {
        personBoxesPanel.add(new Box.Filler(CrewCardPanel.SPACE_MIN_SIZE, CrewCardPanel.SPACE_PREF_SIZE, CrewCardPanel.SPACE_MAX_SIZE));
        personBoxesPanel.add(personBox);
        personBoxesPanel.add(new Box.Filler(CrewCardPanel.SPACE_MIN_SIZE, CrewCardPanel.SPACE_PREF_SIZE, CrewCardPanel.SPACE_MAX_SIZE));

        sessionBoxesPanel.add(new Box.Filler(CrewCardPanel.SPACE_MIN_SIZE, CrewCardPanel.SPACE_PREF_SIZE, CrewCardPanel.SPACE_MAX_SIZE));
        sessionBoxesPanel.add(sessionBox);
        sessionBoxesPanel.add(new Box.Filler(CrewCardPanel.SPACE_MIN_SIZE, CrewCardPanel.SPACE_PREF_SIZE, CrewCardPanel.SPACE_MAX_SIZE));
    }

    public void addExtraPersonBox() {
        if(extraPersonBoxesDeque.size()<10) { // I limit the addition of personBoxes to 10
            JComboBox personBox = View.createDynamicComboBox(new Vector<>(presenter.getAllPersonsVector()),"CREW");
            View.setInitialComboBoxLook(personBox);
            personBox.setMaximumSize(CrewCardPanel.PERSON_BOX_DIMENSION);
            personBox.setAlignmentX(Component.CENTER_ALIGNMENT);
            personBoxesPanel.add(personBox);
            extraPersonBoxesDeque.add(personBox);
            Box.Filler boxFiller = new Box.Filler(CrewCardPanel.SPACE_MIN_SIZE, CrewCardPanel.SPACE_PREF_SIZE, CrewCardPanel.SPACE_MAX_SIZE);
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

    public void addExtraSessionBox() {
        if(extraSessionBoxesDeque.size()<6) { // I limit the addition of personBoxes to 6
            JComboBox sessionBox = View.createDynamicComboBox(new Vector<>(presenter.getAllSessionsVector()),"Papeleta");
            View.setInitialComboBoxLook(sessionBox);
            sessionBox.setMaximumSize(CrewCardPanel.SESSION_BOX_DIMENSION);
            sessionBox.setAlignmentX(Component.CENTER_ALIGNMENT);
            sessionBoxesPanel.add(sessionBox);
            extraSessionBoxesDeque.add(sessionBox);
            Box.Filler boxFiller = new Box.Filler(CrewCardPanel.SPACE_MIN_SIZE, CrewCardPanel.SPACE_PREF_SIZE, CrewCardPanel.SPACE_MAX_SIZE);
            sessionBoxesPanel.add(boxFiller);
            extraSessionBoxFillersDeque.add(boxFiller);
            // Ensure the UI updates to reflect the added component
            sessionBoxesPanel.revalidate();
            sessionBoxesPanel.repaint();
        }
    }

    public void deleteExtraSessionBox() {
        // Ensure there are more than one item in the deque
        if (extraSessionBoxesDeque.size() > 1) {
            // Get and remove the last JComboBox added (but not the first one)
            JComboBox lastSessionBox = extraSessionBoxesDeque.removeLast();

            // Remove the JComboBox from the panel
            sessionBoxesPanel.remove(lastSessionBox);

            // If there are fillers associated, remove the last filler
            if (!extraSessionBoxFillersDeque.isEmpty()) {
                Box.Filler lastFiller = extraSessionBoxFillersDeque.removeLast();
                sessionBoxesPanel.remove(lastFiller);
            }

            // Revalidate and repaint to reflect the changes
            sessionBoxesPanel.revalidate();
            sessionBoxesPanel.repaint();
        }
    }

    public void clearFields() {

    }


    @Override
    public void addActionListeners() {

    }

    // Getters
    public ArrayDeque<JComboBox> getExtraPersonBoxesDeque() {
        return extraPersonBoxesDeque;
    }

    public ArrayDeque<JComboBox> getExtraSessionBoxesDeque() {
        return extraSessionBoxesDeque;
    }

    public JMenuItem getAddGroupItem() {
        return addGroupItem;
    }

    public JMenuItem getDeleteGroupItem() {
        return deleteGroupItem;
    }

    public JMenuItem getAddPersonItem() {
        return addPersonItem;
    }

    public JMenuItem getDeletePersonItem() {
        return deletePersonItem;
    }

    public JMenuItem getAddSessionItem() {
        return addSessionItem;
    }

    public JMenuItem getDeleteSessionItem() {
        return deleteSessionItem;
    }
}
