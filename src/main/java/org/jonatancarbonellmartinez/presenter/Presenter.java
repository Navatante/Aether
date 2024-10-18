package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.view.MainView;

public interface Presenter<T> {
    void setView(T view);
    void setMainViewReference(MainView mainViewReference); // Mostly use to use the method: setLocateRelativeTo(mainView)
}
