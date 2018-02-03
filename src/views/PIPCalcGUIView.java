package views;

import controllers.PIPCalcController;
import processors.*;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.util.Observable;
import java.util.Observer;

/**
 * Created by kdl4u_000 on 11/30/2017.
 */
public class PIPCalcGUIView extends Application implements Observer{
    //the controller for the GUI
    private PIPCalcController controller;
    //the 3 different processors
    private PIPCalcInfixProcessor processor1;
    private PIPCalcPrefixProcessor processor2;
    private PIPCalcPostfixProcessor processor3;

    //calculator's display
    private TextField display;

    /**
     * initialize view, defaults processor to infix processor
     */
    @Override
    public void init() {
        processor1 = new PIPCalcInfixProcessor(); //default
        processor1.addObserver(this);
        processor2 = new PIPCalcPrefixProcessor();
        processor2.addObserver(this);
        processor3 = new PIPCalcPostfixProcessor();
        processor3.addObserver(this);
        this.controller = new PIPCalcController(processor1);
        display = new TextField();
        display.setEditable(false);
    }

    /**
     * Launches main app
     * @param primaryStage first stage with a scene
     */
    @Override
    public void start(Stage primaryStage) {
        final ToggleGroup toggleGroup = new ToggleGroup();

        RadioButton[] calcModeButtons = new RadioButton[3];
        Button[] regularButtons = new Button[28];
        Button[] convertDisplayButtons = new Button[3];

        String[] names1 = {"infix", "prefix", "postfix"};
        String[] names2 = {"1", "2", "3",  "+", "4", "5", "6", "-", "7", "8", "9",
                "*", "Enter", "0", "Clear", "//", "<", "<=", "!=", "^", "==", ">",
                ">=", "(-)", "@", "|", "(", ")"};
        String[] names3 = {"toInfix", "toPrefix", "toPostfix"};

        for (int i = 0; i < calcModeButtons.length; i++) {
            calcModeButtons[i] = new RadioButton(names1[i]);
            calcModeButtons[i].setUserData(names1[i]);
            calcModeButtons[i].setToggleGroup(toggleGroup);
            if (i == 0) calcModeButtons[i].setSelected(true); // default "infix"
        }

        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                String name = new_toggle.getToggleGroup().getSelectedToggle().getUserData().toString();
                System.out.println(name);
                if (new_toggle != null) {
                    switch (name) {
                        case "infix":
                            controller.changeModel(processor1);
                            break;
                        case "prefix":
                            controller.changeModel(processor2);
                            break;
                        case "postfix":
                            controller.changeModel(processor3);
                            break;
                    }
                }
            }
        });

        for (int i = 0; i < regularButtons.length; i++) {
            regularButtons[i] = new Button(names2[i]);
            regularButtons[i].setOnAction(e -> handleButtonAction(e));
        }

        for (int i = 0; i < convertDisplayButtons.length; i++) {
            convertDisplayButtons[i] = new Button(names3[i]);
            convertDisplayButtons[i].setOnAction(e -> handleButtonAction(e));
        }

        BorderPane b = new BorderPane();

        VBox v1 = new VBox(calcModeButtons[0], calcModeButtons[1], calcModeButtons[2],
                convertDisplayButtons[0], convertDisplayButtons[1], convertDisplayButtons[2]);
        b.setTop(display); b.setLeft(makeGrid(regularButtons)); b.setRight(v1);
        primaryStage.setTitle("PIPCalc");
        primaryStage.setScene(new Scene(b, 300, 250));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    /**
     * calc grid
     * @return grid pane
     */
    public GridPane makeGrid(Button[] gridButtons){
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        int index ; boolean done = false;
        for (int row = 0; row < 8 && !done; row++) {
            for (int col = 0; col < 4 && !done; col++) {
                index = 4 * row + col;
                if (index == gridButtons.length) {
                    done = true; break;
                }
                root.add(gridButtons[index], col, row);
            }
        }
        return root;
    }

    /**
     * Just stop
     * @throws Exception
     */
    @Override
    public void stop() throws Exception {
        super.stop();
    }

    /**
     * Updates the view
     * @param o observable
     * @param arg assumes argument
     */
    public void update(Observable o, Object arg) {
        display.setText((String) arg);
    }

    /**
     * is a string numeric or not
     * @param s the string
     * @return true/false
     */
    boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }

    /**
     * handles all button actions
     * @param event event from button or radiobutton
     */
    void handleButtonAction(ActionEvent event) {
        String input = ((Button)event.getSource()).getText();
         if (input.equals("Enter")) {
            controller.process(display.getText());
        }
        else if (input.equals("Clear")){
            display.setText("");
        }
        else if (input.equals("toInfix")) {
            controller.convert(display.getText(), "infix");
        }
        else if (input.equals("toPostfix")) {
            controller.convert(display.getText(), "postfix");
        }
        else if (input.equals("toPrefix")) {
            controller.convert(display.getText(), "prefix");
        }
        else if (isNumeric(input) || input.equals("(") || input.equals(")")) {
            display.setText(display.getText() + input);
        }
        else if (input.equals("(-)")) {
            display.setText(display.getText() + "-");
        }
        else {//operators
             // String lastChar = display.getText().substring(0, display.getText().length() - 1);
            display.setText(display.getText() + input);
        }
    }

}
