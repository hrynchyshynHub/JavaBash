package com.javabash.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Controller {


    @FXML
    private void execute(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER) || event.getCharacter().getBytes()[0] == '\n' || event.getCharacter().getBytes()[0] == '\r') {
            // your action
            System.out.println("Enter pressed");
        }
    }
}
