package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.view.View;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;

public interface PanelPresenter {

    // TODO mirate los metodos de los PanelPresenters y mira cuales son comunes y puedes meterlos en la interfaz para que sigan la misma estructura.



    static void applySearchFilter(String searchText, TableRowSorter<TableModel> sorter) {
        List<RowFilter<TableModel, Object>> filters = new ArrayList<>();

        // Escape special characters in the search text
        String escapedSearchText = escapeSpecialCharacters(searchText);

        // Add the search filter if it's not empty
        if (!escapedSearchText.trim().isEmpty()) {
            filters.add(RowFilter.regexFilter("(?i)" + escapedSearchText)); // Case-insensitive search
        }

        // Combine filters
        RowFilter<TableModel, Object> combinedFilter = null;
        if (!filters.isEmpty()) {
            combinedFilter = RowFilter.andFilter(filters);
        }

        // Set the combined filter or reset if both are null
        sorter.setRowFilter(combinedFilter);
    }

    // Method to escape special regex characters
    static String escapeSpecialCharacters(String text) {
        // This will escape the backslash and any other regex special characters
        return text.replaceAll("([\\\\\\^\\.\\$\\|\\?\\*\\+\\(\\)\\[\\]\\{\\}])", "\\\\$1");
    }

}
