package com.example.vaadinexcercise1.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

public class Form extends VerticalLayout{

    TextField name = new TextField("Name");
    TextField surname = new TextField("Surname");
    TextField email = new TextField("Email");
    TextField phone = new TextField("Phone");

    Button save = new Button("Save");
    Button delete = new Button("Delete...");
    Button close = new Button ("Close");

    Binder<Client> binder = new Binder<>(Client.class);

    public Form () {

        setHeight("400px");
        setWidth("200px");

        binder.bindInstanceFields(this);

        add (
                createFieldsLayouts(),
                createButtonsLayout()
        );
    }

    private Component createFieldsLayouts() {
        VerticalLayout verticalLayout = new VerticalLayout(name,surname,email,phone);
        verticalLayout.setSizeFull();
        return verticalLayout;
    }

    public void setClient(Client client) {
        binder.setBean(client);
    }


    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener( click -> save());
        delete.addClickListener( click -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(click -> fireEvent( new CloseEvent(this)));

        return new HorizontalLayout(delete,close,save);
    }

    private void save () {
        fireEvent(new SaveEvent(this, binder.getBean()));
    }

    public static abstract class ContactFormEvent extends ComponentEvent<Form> {

        private Client client;

        protected ContactFormEvent(Form source, Client client) {
            super(source, false);
            this.client =  client;
        }

        public Client getClient() {
            return client;
        }

    }

    public static class SaveEvent extends ContactFormEvent {

        public SaveEvent(Form source, Client client) {
            super(source, client);
        }

    }

    public static class DeleteEvent extends ContactFormEvent {

        public DeleteEvent(Form source, Client client) {
            super(source, client);
        }

    }

    public static class CloseEvent extends ContactFormEvent {

        public CloseEvent(Form source) {
            super(source, null);
        }

    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
