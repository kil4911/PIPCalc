package views;

import controllers.PIPCalcController;
import processors.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.util.EmptyStackException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by King David Lawrence on 01/20/2018.
 * The GUI for the calculator
 */
public class PIPSolverGUI extends Application implements Observer {
    //the controller for the GUI
    private PIPCalcController controller;
    //the 3 different processors
    private PIPCalcInfixProcessor processor1;
    private PIPCalcPrefixProcessor processor2;
    private PIPCalcPostfixProcessor processor3;
    //boolean determines if some calculation was done just previously
    private boolean justSolved = false;
    //expression that was just solved
    private String lastUserInput = "";
    //stores the most recent answer
    private int lastAnswer = 0;

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
        Button[] regularButtons = new Button[31];
        Button[] convertDisplayButtons = new Button[3];

        String[] names1 = {"infix", "prefix", "postfix"};
        String[] names2 = {"1", "2", "3",  "+", "4", "5", "6", "-", "7", "8", "9",
                "*", "ANS", "0", "DEL", "//", "<", "<=", "!=", "^", ">", ">=", "==",
                "(-)", "@", "|", "(", ")", "ENTER", "CLEAR", "PREV"};
        String[] names3 = {"toInfix", "toPrefix", "toPostfix"};

        for (int i = 0; i < calcModeButtons.length; i++) {
            calcModeButtons[i] = new RadioButton(names1[i]);
            calcModeButtons[i].setUserData(names1[i]);
            calcModeButtons[i].setToggleGroup(toggleGroup);
            if (i == 0) calcModeButtons[i].setSelected(true); // default "infix"
        }

        toggleGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            String name = new_toggle.getToggleGroup().getSelectedToggle().getUserData().toString();
            System.out.println(name);
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
        });

        for (int i = 0; i < regularButtons.length; i++) {
            regularButtons[i] = new Button(names2[i]);
            regularButtons[i].setOnAction(this::handleButtonAction);
        }

        for (int i = 0; i < convertDisplayButtons.length; i++) {
            convertDisplayButtons[i] = new Button(names3[i]);
            convertDisplayButtons[i].setOnAction(e -> handleButtonAction(e));
        }

        BorderPane b = new BorderPane();

        VBox v1 = new VBox(new Label("Mode:"), calcModeButtons[0], calcModeButtons[1], calcModeButtons[2],
                new Label("Convert:"), convertDisplayButtons[0], convertDisplayButtons[1], convertDisplayButtons[2]);
        b.setTop(display); b.setLeft(makeGrid(regularButtons)); b.setRight(v1);
        primaryStage.setTitle("PIPCalc");
        primaryStage.setScene(new Scene(b, 300, 300));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    /**
     * calc grid
     * @return grid pane
     */
    private GridPane makeGrid(Button[] gridButtons){
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        int index ; boolean done = false;
        for (int row = 0; row < 8 && !done; row++) {
            for (int col = 0; col < 4; col++) {
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
     * @throws Exception General exception
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
    private boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }

    /**
     * handles all button actions
     * @param event event from button or radiobutton
     */
    private void handleButtonAction(ActionEvent event) {
        String input = ((Button)event.getSource()).getText();
         if (input.equals("ENTER")) {
             lastUserInput = display.getText();
             String exp = (display.getText().contains("ANS")) ?
                 display.getText().replaceAll("ANS", Integer.toString(lastAnswer)) : display.getText();
             if (!exp.equals("")) {
                 try {
                     controller.process(exp);
                     lastAnswer = Integer.parseInt(display.getText());
                 }
                 catch (ArithmeticException ae) {
                     display.setText("Math Error");
                 }
                 catch (EmptyStackException es) {
                     display.setText("Syntax Error");
                 }

             }
            justSolved = true;
        }
        else if (input.equals("CLEAR")) {
            display.setText("");
        }
        else if (input.equals("PREV")) {
             display.setText(lastUserInput);
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
        else if (isNumeric(input)) {
             if (justSolved && isNumeric(display.getText())) {
                 display.setText("");
                 display.setText(display.getText() + input);
                 justSolved = false;
             }
             else if(display.getText().length() > 0 && display.getText().substring(display.getText().length() - 1).equals("S")) {
                 //do nothing, wait for operator
             }
             else {
                 display.setText(display.getText() + input);
             }
         }
        else if (input.equals("ANS")) {
             String lastChar = display.getText().length() == 0 ? ""
                     : display.getText().substring(display.getText().length() - 1);
             String s;
             if (isNumeric(lastChar) || lastChar.equals("S")) {
                 //do nothing, wait for operator
             }
             else {//lastChar is an operator or ""
                 s = display.getText();
                 display.setText(s + input);
             }
         }
         else if (input.equals("(") || input.equals(")")) {
            display.setText(display.getText() + input);
        }
        else if (input.equals("DEL")) {
             if (!display.getText().equals("")) {
                 String s;
                 if (display.getText().length() > 2 &&
                         display.getText().substring(display.getText().length()-3).equals("ANS")) {
                     s = display.getText().substring(0, display.getText().length() - 3);
                 }
                 else {
                     String lastChar = display.getText().substring(display.getText().length() - 1);
                     s = lastChar.equals(" ") ? display.getText().substring(0, display.getText().length() - 3)
                             : display.getText().substring(0, display.getText().length() - 1);
                 }
                 display.setText(s);
             }
         }
        else if (input.equals("(-)")) {
            display.setText(display.getText() + "-");
        }
        else {//operator work to prevent input expressions with wrong syntax
             if (controller.getModel() instanceof PIPCalcInfixProcessor) {
                 String lastChar = display.getText().length() == 0 ? ""
                         : display.getText().substring(display.getText().length() - 1);
                 if (!lastChar.equals("")) {
                     String s = lastChar.equals(" ") ? display.getText().substring(0, display.getText().length() - 3)
                             : display.getText();
                     display.setText(s.trim() + " " + input + " ");
                 }
             }
             else {
                 display.setText(display.getText() + " " + input + " ");
             }
         }
    }
}
