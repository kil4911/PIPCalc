package views;

import CustomExceptions.MismatchedBracketsException;
import processors.PIPCalcInfixProcessor;
import processors.PIPCalcPostfixProcessor;
import processors.PIPCalcPrefixProcessor;
import processors.PIPCalcProcessor;
import controllers.PIPCalcController;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * Created by King David Lawrence on 01/20/2018.
 * A class representing the text based UI for PIPCalc
 *
 * This has very basic functionality
 */
public class PIPCalcTextView implements Observer{

    private static final String PROMPT = "PIPCalc > ";
    private PIPCalcController controller;

    /**
     * Constructor
     * Defaults to a Infix model
     */
    private PIPCalcTextView(){
        PIPCalcProcessor processor = new PIPCalcInfixProcessor();
        processor.addObserver(this);
        this.controller = new PIPCalcController(processor);
    }

    /**
     * Repeatedly asks for a command to process
     */
    private void mainLoop(){
        Scanner in = new Scanner(System.in);
        String line;

        System.out.print(PROMPT);

        while(in.hasNextLine()){
            line = in.nextLine();

            switch (line) {
                case "Quit":
                    break;
                case "ChangeModel":
                    System.out.print("Enter model type: ");
                    line = in.nextLine();
                    this.controller.changeModel(getTypeFromString(line));
                    break;
                default:
                    try {
                        this.controller.process(line);
                    } catch (MismatchedBracketsException mbs) {
                        System.out.println((mbs.getMessage()));
                    }
            }
            System.out.print(PROMPT);
        }
    }

    /**
     * Function to convert a string type into a model
     * @param type the type of model requested
     * @return the model represented by the provided type
     */
    private PIPCalcProcessor getTypeFromString(String type){
        PIPCalcProcessor processor;
        switch (type) {
            case "infix":
                processor = new PIPCalcInfixProcessor();
                break;
            case "prefix":
                processor = new PIPCalcPrefixProcessor();
                break;
            default:
                processor = new PIPCalcPostfixProcessor();
                break;
        }

        processor.addObserver(this);
        return processor;
    }

    /**
     * Update function for the observer pattern. Just prints the provide arg as an integer.
     * @param o the observable sending the update
     * @param arg the argument being updated. Assumes an integer.
     */
    @Override
    public void update(Observable o, Object arg) {
        System.out.println(arg);
    }

    /**
     * Main function to start the text based UI.
     * @param args not used
     */
    public static void main(String[] args) {
        PIPCalcTextView view = new PIPCalcTextView();
        view.mainLoop();
    }
}