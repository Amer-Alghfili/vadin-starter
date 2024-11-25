package com.example.application.views.channel;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("create-assembly")
public class CreateAssemblyView extends VerticalLayout {
    public CreateAssemblyView() {
        add(new H2("Create Assembly"));

        TextField titleField = new TextField("Name", "Enter title");
        TextField urlField = new TextField("Meeting URL", "Enter URL");
        TextField secretaryField = new TextField("Secretary", "Enter Secretary");
        TextField voteCollectorField = new TextField("Vote Collector", "Enter Vote Collector");

        FormLayout form = new FormLayout(
                titleField,
                urlField,
                new HorizontalLayout(secretaryField, voteCollectorField),
                new Button("Create", e -> {
                    System.out.println("Submitted, form values are: " + titleField.getValue() + ", " + urlField.getValue());
                })
        );

        secretaryField.setWidthFull();
        voteCollectorField.setWidthFull();

        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        add(form);
    }
}
