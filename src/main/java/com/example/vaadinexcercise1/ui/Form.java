package com.example.vaadinexcercise1.ui;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;


public class Form extends FlexLayout {

    Text title = new Text("Edit item");

    TextField name = new TextField("Name");
    TextField surname = new TextField("Surname");
    TextField email = new TextField("Email");
    TextField phone = new TextField("Phone");

    Button save = new Button("Save");
    Button delete = new Button("Delete...");
    Button close = new Button ("Close");

    Binder<Client> binder = new Binder<>(Client.class);

    public Form () {
        setWidth("500px");
        setFlexDirection(FlexDirection.COLUMN);

        binder.bindInstanceFields(this);

        add (
                title,
                createFieldsLayouts(),
                createButtonsLayout()
        );
    }

    private Component createFieldsLayouts() {
        FlexLayout flexLayout1 = new FlexLayout(name,surname,email,phone);
        flexLayout1.setFlexDirection(FlexDirection.COLUMN);
        return flexLayout1;
    }

    public void setClient(Client client) {
        binder.setBean(client);
    }


    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        FlexLayout flexLayout3 =new FlexLayout();

        close.getStyle().set("margin-left","auto");

        flexLayout3.add(delete,close,save);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener( click -> save());
        delete.addClickListener( click -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(click -> fireEvent( new CloseEvent(this)));

        return flexLayout3;
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