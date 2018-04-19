package views;

import Util.customExceptions.MismatchedBracketsException;
import controllers.PIPCalcController;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import processors.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


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
    /*Styles*/
    private static final int sceneWidth = 330;
    private static final int sceneHeight = 365;
    private static final int convertBtnsWidth = 95;
    private static final int convertBtnsHeight = 24;
    private static final int displayHeight = 60;
    private static final int regBtnsHeight = 35;
    private static final int regBtnsWidth = 50;
    private static final int gridVgap = 10;
    private static final int gridHgap = 10;
    private static final int ctrlBtnsWidth = 95;
    private static final int ctrlBtnsHeight = 38;


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
        display.setPrefHeight(displayHeight);
        display.setEditable(false);
        display.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));
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
        Button[] convertButtons = new Button[3];
        Button[] controlButtons = new Button[3];

        String[] names1 = {"infix", "prefix", "postfix"};
        String[] names2 = {"1", "2", "3",  "+", "4", "5", "6", "-", "7", "8", "9",
                "*", "ANS", "0", "DEL", "//", "space",  "<", "<=", "^", "!=", ">", ">=", "@", "==", "(", ")", "(-)"};
        //Brackets at position length - 1, length - 2
        String[] names3 = {"toInfix", "toPrefix", "toPostfix"};
        String[] names4 = {"=", "CLEAR", "HISTORY"};

        makeButtons(regularButtons, names2, regBtnsWidth, regBtnsHeight);
        makeButtons(convertButtons, names3, convertBtnsWidth, convertBtnsHeight);
        makeButtons(controlButtons, names4, ctrlBtnsWidth, ctrlBtnsHeight);

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
                    regularButtons[regularButtons.length - 1].setDisable(false);
                    regularButtons[regularButtons.length - 2].setDisable(false);
                    break;
                case "prefix":
                    controller.changeModel(processor2);
                    regularButtons[regularButtons.length - 1].setDisable(true);
                    regularButtons[regularButtons.length - 2].setDisable(true);
                    break;
                case "postfix":
                    controller.changeModel(processor3);
                    regularButtons[regularButtons.length - 1].setDisable(true);
                    regularButtons[regularButtons.length - 2].setDisable(true);
                    break;
            }
        });

        //GUI

        BorderPane borderPane = new BorderPane();


        VBox modeBox = new VBox(new Label("Mode:"), calcModeButtons[0], calcModeButtons[1], calcModeButtons[2]);

        VBox convertBox = new VBox(convertButtons[0], convertButtons[1],
                convertButtons[2]);
        convertBox.setSpacing(3);

        VBox controlsBox = new VBox(controlButtons[0], controlButtons[1], controlButtons[2]);
        controlsBox.setPadding(new Insets(2, 0, 0, 0));
        controlsBox.setSpacing(3);

        VBox rightBox = new VBox(modeBox, controlsBox, new Label("Convert:"), convertBox);
        rightBox.setPadding(new Insets(0, 0, 0, 5));

        HBox centreBox = new HBox(makeGrid(regularButtons), rightBox);
        centreBox.setPadding(new Insets(5, 0, 0, 5));

        borderPane.setTop(display); borderPane.setLeft(centreBox);
        borderPane.getStyleClass().add("borderPane"); //Invoke CSS

        Scene scene = new Scene(borderPane, sceneWidth, sceneHeight);
        scene.getStylesheets().add("Util/css/PIPStyles.css"); //make styles accessible to all nodes

        primaryStage.setTitle("PIPSolver");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Just stop
     * @throws Exception General exception
     */
    @Override
    public void stop() throws Exception { super.stop(); }

    /**
     * Updates the view
     * @param o observable
     * @param arg assumes argument
     */
    public void update(Observable o, Object arg) {
        display.setText((String) arg);
    }

    /**
     * calc grid
     * @return grid pane
     */
    private GridPane makeGrid(Button[] gridButtons){
        GridPane grid = new GridPane();
        int index ; boolean done = false;
        grid.setVgap(gridVgap);
        grid.setHgap(gridHgap);
        for (int row = 0; row < 7 && !done; row++) {
            for (int col = 0; col < 4; col++) {
                index = 4 * row + col;
                if (index == gridButtons.length) {
                    done = true; break;
                }
                grid.add(gridButtons[index], col, row);
            }
        }
        return grid;
    }

    /**
     * Make & Styles buttons for a particular section of the app
     * @param buttons the array of buttons to make
     * @param names the names of the buttons
     * @param btnWidth the width of each button
     * @param btnHeight the height of each button
     */
    private void makeButtons (Button[] buttons, String[] names, int btnWidth, int btnHeight) {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button(names[i]);
            buttons[i].setPrefWidth(btnWidth);
            buttons[i].setPrefHeight(btnHeight);
            buttons[i].setOnAction(this::handleButtonAction);
            buttons[i].getStyleClass().add("buttons");
            String name = buttons[i].getText();
            if (name.equals("=")) {
                buttons[i].setFont(Font.font("Verdana", FontWeight.BLACK, 18));
                buttons[i].getStyleClass().add("enterButton");
            }
            else if (name.equals("CLEAR") || name.equals("HISTORY")) {
                buttons[i].setFont(Font.font("Verdana", FontWeight.BLACK, 14));
                buttons[i].getStyleClass().add("regularButtons");
            }
            else if (name.equals("toInfix") || name.equals("toPrefix") || name.equals("toPostfix")) {
                buttons[i].setFont(Font.font("Verdana", FontWeight.BLACK,12));
                buttons[i].getStyleClass().add("regularButtons");
            }
            else if (name.equals("ANS") || name.equals("DEL")) {
                buttons[i].setFont(Font.font("Verdana", FontWeight.BOLD,13));
                buttons[i].getStyleClass().add("regularButtons");
            }
            else if(isNumeric(name)) {
                buttons[i].setFont(Font.font("Verdana", 18));
            }
            else if(name.equals("space")) {
                buttons[i].setText("");
                buttons[i].getStyleClass().add("regularButtons");
                final int x = i;
                buttons[i].setFont(Font.font("Verdana", FontWeight.BOLD, 10));
                buttons[i].hoverProperty().addListener((ov, oldValue, newValue) -> {
                    if (newValue) {
                        buttons[x].setText("space");
                    } else {
                        buttons[x].setText("");
                    }
                });
            }
            else {
                buttons[i].setFont(Font.font("Verdana", 15));
                buttons[i].getStyleClass().add("regularButtons");
            }
        }
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
    private void handleButtonAction(ActionEvent event){
        String input = ((Button)event.getSource()).getText();
         if (input.equals("=")) {
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
                 catch (MismatchedBracketsException mbs) {
                     display.setText(mbs.getMessage());
                 }
             }
            justSolved = true;
        }
        else if(input.equals("(-)")) {

             String lastChar = display.getText().length() == 0 ? ""
                     : display.getText().substring(display.getText().length() - 1);
             if (lastChar.equals("") || lastChar.equals(" ")) {
                 display.setText(display.getText() + "-");
             }
             else if (lastChar.equals("-") || lastChar.equals(")") || isNumeric(lastChar)) {
                 display.setText(display.getText());
             }
         }
        else if (input.equals("CLEAR")) {
            display.setText("");
        }
         else if (input.equals("space")) {
             display.setText(display.getText() + " ");
         }
        else if (input.equals("HISTORY")) {
             display.setText(lastUserInput);
        }
        else if (input.equals("toInfix")) {
             try {
                 controller.convert(display.getText(), "infix");
             } catch (MismatchedBracketsException mbs) {
                 display.setText(mbs.getMessage());
             }
         }
        else if (input.equals("toPostfix")) {
             try {
                 controller.convert(display.getText(), "postfix");
             } catch (MismatchedBracketsException mbs) {
                 display.setText(mbs.getMessage());
             }
         }
        else if (input.equals("toPrefix")) {
             try {
                 controller.convert(display.getText(), "prefix");
             } catch (MismatchedBracketsException mbs) {
                 display.setText(mbs.getMessage());
             }
         }
        else if (isNumeric(input)) {
             String lastChar = display.getText().length() == 0 ? ""
                     : display.getText().substring(display.getText().length() - 1);
             if (justSolved && isNumeric(display.getText())) {
                 display.setText("");
                 display.setText(display.getText() + input);
                 justSolved = false;
             }
             else if (lastChar.equals(")")) {
                 display.setText(display.getText() + " * " + input);
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
         else if (input.equals("(")) {
             if (display.getText().equals("")) {
                 display.setText(display.getText() + input + " ");
             }
             else {
                 String lastChar = display.getText().substring(display.getText().length() - 1);
                 if (isNumeric(lastChar))
                     display.setText(display.getText() + " * " + input + " ");
                 else
                     display.setText(display.getText() + " " + input + " ");
             }

        }
        else if( input.equals(")")) {
             display.setText(display.getText() + " " + input);
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
        else {//Operators: prevent input expressions with wrong syntax
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
