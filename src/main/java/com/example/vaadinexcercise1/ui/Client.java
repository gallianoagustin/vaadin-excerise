package com.example.vaadinexcercise1.ui;

import com.vaadin.flow.component.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    private String name;
    private String surname;
    private String email;
    private String phone;
}
