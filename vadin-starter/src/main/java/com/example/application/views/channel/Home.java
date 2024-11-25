package com.example.application.views.channel;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.awt.*;
import java.util.List;

@Route("")
public class Home extends VerticalLayout {
    public Home() {
        HorizontalLayout boxes = new HorizontalLayout();
        boxes.setJustifyContentMode(JustifyContentMode.CENTER);
        boxes.getStyle().set("gap", "1em");

        @Tag("div")
        class Box extends VerticalLayout {
            private int counter = 0;

            public Box(String title, String value) {
                setWidth("16em");
                getStyle()
                        .setBorderRadius("14px")
                        .setBackground("black")
                        .setColor("white")
                        .setPadding("1.25em");

                addClickListener(e -> {
                    counter++;
                    getUI().ifPresent(ui -> ui.access(() -> {
                        Div counterDiv = (Div) getChildren().toArray()[2];
                        counterDiv.setText(String.valueOf(counter));
                    }));
                });

                add(
                        new Div(title),
                        new Div(value),
                        new Div(String.valueOf(counter))
                );
            }
        }

        boxes.add(getBoxes().stream().map(box -> new Box(box.title(), box.value())).toArray(Component[]::new));

        RouterLink createAssemblyLink = new RouterLink("Create new Assembly", CreateAssemblyView.class);
        createAssemblyLink.getStyle()
                .setBackground("green")
                .setColor("white")
                .setPadding("0.5em")
                .setBorderRadius("14px");

        add(boxes, createAssemblyLink);
    }

    record BoxData(String title, String value) {
    }

    private List<BoxData> getBoxes() {
        return List.of(
                new BoxData("Shareholder Assemblies", "0"),
                new BoxData("ESOPS", "20"),
                new BoxData("Meetings", "44")
        );
    }
}