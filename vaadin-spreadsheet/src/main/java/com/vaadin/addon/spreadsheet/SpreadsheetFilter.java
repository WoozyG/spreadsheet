package com.vaadin.addon.spreadsheet;

import java.util.Set;

/**
 * Interface for filter components that can be added to a
 * {@link SpreadsheetFilterTable}. Filtering is done by simply hiding the
 * table's rows that are filtered.
 * <p>
 * Add / remove filters from table with
 * {@link SpreadsheetFilterTable#registerFilter(org.vaadin.spreadsheet.PopupButton, SpreadsheetFilter)}
 * and
 * {@link SpreadsheetFilterTable#unRegisterFilter(org.vaadin.spreadsheet.PopupButton, SpreadsheetFilter)}.
 * <p>
 * When a filter been has updated (by server side / user actions),
 * {@link SpreadsheetFilterTable#onFiltersUpdated()} should be called.
 */
public interface SpreadsheetFilter {

    /**
     * Clear the filtering options. After this method the
     * {@link #getFilteredRows()} for this filter should return an empty set.
     */
    public void clearFilter();

    /**
     * Returns the rows that should be filtered by this filter.
     * 
     * @return 0-based filtered row indexes
     */
    public Set<Integer> getFilteredRows();
}