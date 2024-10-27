package org.jonatancarbonellmartinez.app;

import org.jonatancarbonellmartinez.utilities.DatabasePresenter;
import org.jonatancarbonellmartinez.utilities.DbFileChooserView;
import org.jonatancarbonellmartinez.view.*;

public class AppInitializer {

    public AppInitializer() {
        new DatabasePresenter(new DbFileChooserView());
        new MainView().setVisible(true);
    }
}
