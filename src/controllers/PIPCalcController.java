package controllers;

import processors.PIPCalcProcessor;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by King David Lawrence on 01/20/2018.
 * The controller for the text and UI App
 */
public class PIPCalcController {

    //the model for the controller; can be infix, prefix or postfix processors
    private PIPCalcProcessor model;

    /**
     * Constructor for PIPCAlcText Controller.
     *
     * The scanner and writer allow for file or command line
     * input/output
     *
     * @param model the initial model for this controllers
     */
    public PIPCalcController(PIPCalcProcessor model){
        this.model = model;
    }

    /**
     * changes the current model to the provided model
     * @param model the model to change to
     */
    public void changeModel(PIPCalcProcessor model){
        this.model = model;
    }

    /**
     * getter for model field
     * @return current model
     */
    public PIPCalcProcessor getModel () { return model; }

    /**
     * Constructs and evaluates the provided string using the current model
     *
     * Note: It does not return the result. The model should update the views.
     * @param statement the string representing an expression to process
     */
    public void process(String statement){
        ArrayList<String> tokens =
                new ArrayList<>(Arrays.asList(statement.split(" ")));
        this.model.constructTree(tokens);
        this.model.evaluateTree();
    }

    /**
     * Converts the provided expression into the requested form.
     * @param statement the string representing the expression to convert
     * @param mode the mode to convert the expression into
     */
    public void convert(String statement, String mode){
        ArrayList<String> tokens =
                new ArrayList<>(Arrays.asList(statement.split(" ")));
        this.model.constructTree(tokens);
        this.model.displayTree(mode);
    }
}