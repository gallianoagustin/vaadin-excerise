package com.example.vaadinexcercise1.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Route
public class MainView extends VerticalLayout {

    private final Form form;
    Grid<Client> grid = new Grid<>(Client.class);
    List<Client> clientList = new ArrayList<>();
    Button btn;
    Dialog dialog;
    public MainView () {

        setSizeFull();
        configureGrid();

        clientList.add(new Client("Lucas", "Kane", "lkane@gmail.com", "1123123123"));
        clientList.add(new Client("Peter", "Buchanan", "pbuchanan@gmail.com", "267182912"));
        clientList.add(new Client("Samuel", "Lee", "slee@gmail.com", "6524382912"));
        clientList.add(new Client("Aaron", "Akiston", "aakiston@gmail.com", "6512891222"));

        updateList();

        form = new Form();

        form.addListener(Form.SaveEvent.class,this::saveClient);
        form.addListener(Form.DeleteEvent.class,this::deleteClient);
        form.addListener(Form.CloseEvent.class, e -> closeEditor());


        btn = new Button("New Item");
        btn.addClickListener( click -> addClient());
        btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Div content2 = new Div(form);

        dialog = new Dialog();
        dialog.add(content2);

        grid.setHeight("250px");
        grid.addComponentColumn(this::function);

        FlexLayout flexLayout = new FlexLayout();

        flexLayout.add(btn);
        flexLayout.setFlexDirection(FlexLayout.FlexDirection.ROW_REVERSE);
        Div content = new Div(grid, flexLayout);
        content.setSizeFull();

        add(content);
        closeEditor();
    }

    private Button function(Client item) {
        Icon vaadinIcon = VaadinIcon.EDIT.create();
        vaadinIcon.setColor("orange");
        Button btn = new Button("", vaadinIcon);
        btn.addClickListener( e -> editContact(item));
        return btn;
    }

    private void addClient() {
        grid.asSingleSelect().clear();
        editContact(new Client());
    }

    private void deleteClient(Form.DeleteEvent evt) {
        Optional<Client> first = clientList.stream().filter(x -> x.getEmail().equals(evt.getClient().getEmail()))
                .findFirst();
        clientList.remove(first.get());
        updateList();
        closeEditor();
    }

    private void saveClient(Form.SaveEvent evt) {
        boolean a = clientList.stream().allMatch(p -> p.getEmail()!=evt.getClient().getEmail());
        if(a) clientList.add(evt.getClient());
        updateList();
        closeEditor();
    }

    private void updateList() {
        grid.setItems(clientList);
    }

    private void configureGrid() {
        grid.setSizeFull();
        //grid.asSingleSelect().addValueChangeListener(evt -> editContact(evt.getValue()));
    }

    private void editContact(Client value) {
        form.setClient(value);
        form.setVisible(true);
        dialog.open();
    }

    public void closeEditor () {
        form.setClient(null);
        form.setVisible(false);
        dialog.close();
    }
}