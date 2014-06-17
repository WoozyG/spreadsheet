package com.vaadin.addon.spreadsheet.client;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ui.VWindow;

public class PopupButtonHeader extends Widget {

    protected final static String CLASSNAME = PopupButtonWidget.POPUP_OVERLAY_CLASSNAME
            + "-header";
    protected final static String CAPTION_CLASSNAME = "header-caption";

    private DivElement root = Document.get().createDivElement();
    private DivElement close = Document.get().createDivElement();
    private DivElement caption = Document.get().createDivElement();
    private PopupPanel popup;
    private SheetWidget sheetWidget;

    public PopupButtonHeader() {
        root.setClassName(CLASSNAME);
        close.setClassName(VWindow.CLASSNAME + "-closebox");
        close.setAttribute("role", "button");
        caption.setClassName("header-caption");
        root.appendChild(close);
        root.appendChild(caption);
        DOM.sinkEvents((Element) close.cast(), Event.ONCLICK);
        DOM.setEventListener((Element) close.cast(), this);

        setElement(root);
    }

    public void setCaption(String caption) {
        this.caption.setInnerText(caption);
    }

    public void setPopup(PopupPanel popup) {
        this.popup = popup;
    }

    public void setSheet(SheetWidget sheetWidget) {
        this.sheetWidget = sheetWidget;
    }

    @Override
    public void onBrowserEvent(Event event) {
        if (event.getEventTarget().equals(close)) {
            popup.hide();
            sheetWidget.focusSheet();
        } else {
            super.onBrowserEvent(event);
        }
    }

    public void setHidden(boolean headerHidden) {
        getElement().getStyle().setDisplay(
                headerHidden ? Display.NONE : Display.BLOCK);
    }

    public boolean isHidden() {
        return Display.NONE.getCssName().equals(
                getElement().getStyle().getDisplay());
    }
}