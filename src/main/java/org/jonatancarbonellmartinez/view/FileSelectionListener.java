package org.jonatancarbonellmartinez.view;

public interface FileSelectionListener {
    void onFileSelected(String filePath);
    void onFileSelectionCanceled();
}
