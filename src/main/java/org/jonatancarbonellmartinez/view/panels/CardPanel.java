package org.jonatancarbonellmartinez.view.panels;

import javax.swing.*;
import java.awt.*;

public interface CardPanel {
    Dimension HOUR_FIELD_DIMENSION = new Dimension(40,25);
    Dimension PERSON_BOX_DIMENSION = new Dimension(80,25);

    JTextField getDayHourField();
    JTextField getNightHourField();
    JTextField getGvnHourField();
    JComboBox getCrewBox();
}
