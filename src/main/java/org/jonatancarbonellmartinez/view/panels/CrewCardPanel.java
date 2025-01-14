package org.jonatancarbonellmartinez.view.panels;

import javax.swing.*;
import java.awt.*;

public interface CrewCardPanel {
    Dimension HOUR_FIELD_DIMENSION = new Dimension(40,25);
    Dimension PROJECTILE_FIELD_DIMENSION = new Dimension(50,25);
    Dimension PERSON_BOX_DIMENSION = new Dimension(80,25);
    Dimension SESSION_BOX_DIMENSION = new Dimension(150,25);
    Dimension SPACE_MIN_SIZE = new Dimension(5,5);
    Dimension SPACE_PREF_SIZE = new Dimension(5,5);
    Dimension SPACE_MAX_SIZE = new Dimension(5,5);

    JTextField getDayHourField();
    JTextField getNightHourField();
    JTextField getGvnHourField();
    JComboBox getCrewBox();
}
