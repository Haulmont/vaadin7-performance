package com.haulmont.vaadin.sample;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

public class SampleVaadinUI extends UI {

    int n = 10;
    int c = 10;
    int r = 10;

    VerticalLayout layout;
    TextField text1;
    TextField text2;
    TextField text3;

    @Override
    protected void init(VaadinRequest request) {
        initMain();
    }

    protected void initMain() {
        layout = new VerticalLayout();
        layout.setStyleName("main");
        layout.setSpacing(true);
        layout.setSizeFull();
        setContent(layout);

        setSizeFull();

        HorizontalLayout hbox = new HorizontalLayout();
        hbox.setSpacing(true);
        layout.addComponent(hbox);

        text1 = new TextField();
        text1.setCaption("vbox count");
        text1.setRequired(true);
        text1.setValue(n + "");
        hbox.addComponent(text1);

        text2 = new TextField();
        text2.setCaption("rows count * 6");
        text2.setRequired(true);
        text2.setValue(r + "");
        hbox.addComponent(text2);

        text3 = new TextField();
        text3.setCaption("columns count * 3");
        text3.setRequired(true);
        text3.setValue(c + "");
        hbox.addComponent(text3);

        Button btn = new Button("repaint");
        btn.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent clickEvent) {
                repaint();
            }
        });
        hbox.addComponent(btn);
    }

    protected void repaint() {
        try {
            n = Integer.parseInt(text1.getValue());
        } catch (NumberFormatException e) {
            n = 10;
        }

        try {
            r = Integer.parseInt(text2.getValue());
        } catch (NumberFormatException e) {
            r = 10;
        }

        try {
            c = Integer.parseInt(text3.getValue());
        } catch (NumberFormatException e) {
            c = 10;
        }
        initMain();

        VerticalLayout prevL = layout;
        for (int i = 0; i < n; i++) {
            VerticalLayout curL = new VerticalLayout();
            curL.setSpacing(true);
            curL.setSizeFull();
            prevL.addComponent(curL);
            prevL.setExpandRatio(curL, 1);
            prevL = curL;
        }

        Table table = createTable();
        prevL.addComponent(table);
        prevL.setExpandRatio(table, 1);
    }

    protected Table createTable() {
        Table table = new Table("This is my Table in " + n + " VerticalBox");
        table.setSizeFull();
        table.setSelectable(true);

        /* Define the names and data types of columns.
         * The "default value" parameter is meaningless here. */

        for (int i = 0; i < c; i++) {
            table.addContainerProperty("First Name" + i, String.class, null);
            table.addContainerProperty("Last Name" + i, String.class, null);
            table.addContainerProperty("Year" + i, Integer.class, null);
        }

        /* Add a few items in the table. */
        int j = 0;
        for (int i = 0; i < r; i++) {
            table.addItem(makeRow(new Object[]{"Nicolaus" + i, "Copernicus", 1473}, c), j++);
            table.addItem(makeRow(new Object[]{"Tycho" + i, "Brahe", 1546}, c), j++);
            table.addItem(makeRow(new Object[]{"Giordano" + i, "Bruno", 1548}, c), j++);
            table.addItem(makeRow(new Object[]{"Galileo" + i, "Galilei", 1564}, c), j++);
            table.addItem(makeRow(new Object[]{"Johannes" + i, "Kepler", 1571}, c), j++);
            table.addItem(makeRow(new Object[]{"Isaac" + i, "Newton", 1643}, c), j++);
        }

        return table;
    }

    protected Object[] makeRow(Object[] data, int c) {
        Object[] row = new Object[c * data.length];
        for (int j = 0; j < c; j++) {
            int x = 0;
            for (Object value : data) {
                row[j * data.length + x] = value;
                x++;
            }
        }

        return row;
    }
}