package com.example.vaadinexcercise1.ui;

import com.vaadin.flow.component.Component;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Client {

    private int id;
    private String name;
    private String surname;
    private String email;
    private String phone;

    public Client() {

    }
}
