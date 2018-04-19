package com.javabash.controller;

import com.javabash.engine.Line;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class Controller implements Initializable{


    @FXML
    private TextArea mainFrame;

    private List<String> commands = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Line line = new Line();
        mainFrame.appendText(line.getUser() + line.getSeparator());

        mainFrame.textProperty().addListener((observable, oldValue, newValue) -> {
            //TODO: make readonly pieces of text which was executed;

            if(!newValue.startsWith("root:~$")){
                mainFrame.setText(oldValue);
            }
        });
    }

    @FXML
    private void execute(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER) || event.getCharacter().getBytes()[0] == '\n' || event.getCharacter().getBytes()[0] == '\r') {
            Stream<String> comands = Arrays.stream(mainFrame.getText().split("\n")).filter( x -> x.startsWith("root:~$"));
            String lastLine  = comands.reduce((first, second) -> second).orElse(null);

            System.out.println("last line ="  + lastLine);

            Line line = Line.parseLine(lastLine);

            System.out.println(line.getCommand() + " = command");

            mainFrame.appendText("\n" + executeBashCommand(line.getCommand()));

            mainFrame.appendText("\nroot:~$ ");
            event.consume();


        }
    }


    /**
     * Execute a bash command. We can handle complex bash commands including
     * multiple executions (; | && ||), quotes, expansions ($), escapes (\), e.g.:
     *     "cd /abc/def; mv ghi 'older ghi '$(whoami)"
     * @param command
     * @return true if bash got started, but your command may have failed.
     */
    public static String executeBashCommand(String command) {
        boolean success = false;
        StringBuilder result = new StringBuilder();
        System.out.println("Executing BASH command:\n   " + command);
        Runtime r = Runtime.getRuntime();

        String[] commands = {"cmd", "/c", command};
        try {
            Process p = r.exec(commands);

            p.waitFor();
            BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";

            while ((line = b.readLine()) != null) {
                result.append(line + "\n");
            }

            b.close();
            success = true;
        } catch (Exception e) {
            System.err.println("Failed to execute bash with command: " + command);
            e.printStackTrace();
        }
        return result.toString();
    }
}
