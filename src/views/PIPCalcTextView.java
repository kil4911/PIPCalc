package views;

import processors.PIPCalcInfixProcessor;
import processors.PIPCalcPostfixProcessor;
import processors.PIPCalcPrefixProcessor;
import processors.PIPCalcProcessor;
import controllers.PIPCalcController;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * A class representing the text based UI for PIPCalc
 *
 * This has very basic functionality
 */
public class PIPCalcTextView implements Observer{

    public static final String PROMPT = "PIPCalc > ";
    private PIPCalcController controller;

    /**
     * Constructor
     * Defaults to a Infix model
     */
    public PIPCalcTextView(){
        PIPCalcProcessor processor = new PIPCalcInfixProcessor();
        processor.addObserver(this);
        this.controller = new PIPCalcController(processor);
    }

    /**
     * Repeatedly asks for a command to process
     */
    public void mainLoop(){
        Scanner in = new Scanner(System.in);
        String line;

        System.out.print(PROMPT);

        while(in.hasNextLine()){
            line = in.nextLine();

            if(line.equals("Quit")){
                break;
            }
            else if(line.equals("ChangeModel")){
                System.out.print("Enter model type: ");
                line = in.nextLine();
                this.controller.changeModel(getTypeFromString(line));
            }
            else{
                this.controller.process(line);
            }

            System.out.print(PROMPT);
        }
    }

    /**
     * Function to convert a string type into a model
     * @param type the type of model requested
     * @return the model represented by the provided type
     */
    public PIPCalcProcessor getTypeFromString(String type){
        PIPCalcProcessor processor;
        if(type.equals("infix")){
            processor = new PIPCalcInfixProcessor();
        }
        else if(type.equals("prefix")){
            processor = new PIPCalcPrefixProcessor();
        }
        else{
            processor = new PIPCalcPostfixProcessor();
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
     * @param args
     */
    public static void main(String[] args) {
        PIPCalcTextView view = new PIPCalcTextView();
        view.mainLoop();
    }
}