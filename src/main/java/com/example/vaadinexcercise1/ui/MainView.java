package com.example.vaadinexcercise1.ui;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Route
public class MainView extends VerticalLayout {

    private Form form = new Form();
    Grid<Client> grid = new Grid<>(Client.class);
    List<Client> clientList = new ArrayList<>();
    Button btn;

    public MainView () {

        setSizeFull();
        configureGrid();

        clientList.add(new Client(1,"Lucas", "Kane", "lkane@gmail.com", "1123123123"));
        clientList.add(new Client(2,"Peter", "Buchanan", "pbuchanan@gmail.com", "267182912"));
        clientList.add(new Client(3,"Samuel", "Lee", "slee@gmail.com", "6524382912"));
        clientList.add(new Client(4,"Aaron", "Akiston", "aakiston@gmail.com", "6512891222"));

        updateList();

        form = new Form();

        form.addListener(Form.SaveEvent.class,this::saveClient);
        form.addListener(Form.DeleteEvent.class,this::deleteClient);
        form.addListener(Form.CloseEvent.class, e -> closeEditor());


        btn = new Button("New Item");
        btn.addClickListener( click -> addClient());

        Div content = new Div(grid,form, btn);
        content.setSizeFull();



        add(content);
        closeEditor();
    }

    private void addClient() {
        grid.asSingleSelect().clear();
        editContact(new Client());
    }

    private void deleteClient(Form.DeleteEvent evt) {
        Optional<Client> first = clientList.stream().filter(x -> x.getId() == evt.getClient().getId())
                .findFirst();
        clientList.remove(first.get());
        updateList();
        closeEditor();
    }

    private void saveClient(Form.SaveEvent evt) {
        /*
        Optional<Client> first = clientList.stream().filter(x -> x.getId() == evt.getClient().getId())
                .findFirst();
        clientList.add(evt.getClient());
         */
        updateList();
        closeEditor();
    }

    private void updateList() {
        grid.setItems(clientList);
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.asSingleSelect().addValueChangeListener(evt -> editContact(evt.getValue()));
    }

    private void editContact(Client value) {
        form.setClient(value);
        form.setVisible(true);
    }

    public void closeEditor () {
        form.setClient(null);
        form.setVisible(false);
    }

    /*
    public Button function(Client item, List<Client> clientList) {

        Client client;
        form.setClient(item,clientList);
        Button btn = new Button("", VaadinIcon.TABLE.create());
        btn.addClickListener(buttonClickEvent -> add(form));


    }


     */
}
