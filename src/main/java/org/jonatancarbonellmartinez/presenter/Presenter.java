package org.jonatancarbonellmartinez.presenter;

public interface Presenter<T> {
    String getView(T t);
}
