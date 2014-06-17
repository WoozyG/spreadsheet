package com.vaadin.addon.spreadsheet;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

/**
 * Represents a "table" inside a spreadsheet. A table is a region (
 * {@link CellRangeAddress}), that has {@link PopupButton} on the column header
 * cells of the region.
 */
public class SpreadsheetTable {

    private final CellRangeAddress fullTableRegion;
    private final Sheet sheet;
    private final Spreadsheet spreadsheet;
    protected final Map<CellReference, PopupButton> popupButtons;

    /**
     * Creates a new table for the given spreadsheet component, its active sheet
     * (returned by {@link Spreadsheet#getActiveSheet()}) and region. Adds
     * pop-up buttons for table headers (cells in the first row).
     * 
     * @param spreadsheet
     * @param tableRegion
     */
    public SpreadsheetTable(Spreadsheet spreadsheet,
            CellRangeAddress tableRegion) {
        this(spreadsheet, spreadsheet.getActiveSheet(), tableRegion);
    }

    /**
     * Creates a new table for the given spreadsheet component, sheet and
     * region. If the component is currently displaying the sheet that the table
     * belongs to, pop-up buttons are added to table headers (first row cells).
     * 
     * @param spreadsheet
     * @param sheet
     * @param fullTableRegion
     */
    public SpreadsheetTable(Spreadsheet spreadsheet, Sheet sheet,
            CellRangeAddress fullTableRegion) {
        this.spreadsheet = spreadsheet;
        this.sheet = sheet;
        this.fullTableRegion = fullTableRegion;
        popupButtons = new HashMap<CellReference, PopupButton>();

        if (isTableSheetCurrentlyActive()) {
            initPopupButtons();
        }
    }

    /**
     * Reload the table's pop-up buttons, if spreadsheet component is currently
     * presenting the sheet this table belongs to.
     * <p>
     * If there are no popup buttons stored, when {@link #clear()} has been
     * called, the popup-buttons are recreated. Otherwise they are just added to
     * the spreadsheet component again.
     */
    public void reload() {
        if (isTableSheetCurrentlyActive()) {
            if (popupButtons.isEmpty()) {
                initPopupButtons();
            } else {
                for (PopupButton popupButton : popupButtons.values()) {
                    spreadsheet.addPopupButton(popupButton);
                }
            }
        }
    }

    /**
     * Clears all the table's pop-up buttons and their pop-up content.
     */
    public void clear() {
        for (PopupButton popupButton : popupButtons.values()) {
            popupButton.removeAllComponents();
        }
        popupButtons.clear();
    }

    /**
     * Returns true if the spreadsheet component is currently displaying the
     * sheet that this table belongs to.
     * 
     * @return
     */
    public boolean isTableSheetCurrentlyActive() {
        return spreadsheet.getActiveSheet().equals(sheet);
    }

    protected void initPopupButtons() {
        if (sheet.equals(spreadsheet.getActiveSheet())) {
            for (int c = fullTableRegion.getFirstColumn(); c <= fullTableRegion
                    .getLastColumn(); c++) {
                CellReference popupButtonCellReference = new CellReference(
                        fullTableRegion.getFirstRow(), c);
                PopupButton popupButton = new PopupButton(
                        popupButtonCellReference);
                popupButtons.put(popupButtonCellReference, popupButton);
                spreadsheet.addPopupButton(popupButton);
            }
        }
    }

    /**
     * Gets the {@link Sheet} this table belongs to.
     * 
     * @return
     */
    public Sheet getSheet() {
        return sheet;
    }

    /**
     * Gets the {@link Spreadsheet} component this table belongs to.
     * 
     * @return
     */
    public Spreadsheet getSpreadsheet() {
        return spreadsheet;
    }

    /**
     * Gets the full table region, {@link CellRangeAddress} for this table.
     * 
     * @return
     */
    public CellRangeAddress getFullTableRegion() {
        return fullTableRegion;
    }

    /**
     * Gets the {@link PopupButton} for the given column. If given column is
     * outside of the table region, <code>null></code> will be returned.
     * 
     * @param col
     *            0-based
     * @return the {@link PopupButton} contained in the given column header for
     *         this table.
     */
    public PopupButton getPopupButton(int col) {
        return getPopupButton(new CellReference(fullTableRegion.getFirstRow(),
                col));
    }

    /**
     * Gets the {@link PopupButton} for the header cell pointed by
     * {@link CellReference}. If given reference is not a header cell for this
     * table, or is outside of the table region, <code>null></code> will be
     * returned.
     * 
     * @param filterCellReference
     *            header cell reference
     * @return
     */
    public PopupButton getPopupButton(CellReference filterCellReference) {
        return popupButtons.get(filterCellReference);
    }

    /**
     * Returns all of the {@link PopupButton}s for this table.
     * 
     * @return the pop-up buttons for this table in no specific order.
     */
    public Collection<PopupButton> getPopupButtons() {
        return Collections.unmodifiableCollection(popupButtons.values());
    }

}