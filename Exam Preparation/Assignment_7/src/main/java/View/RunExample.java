package View;

import Controller.Controller;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.InterpreterException;
import Exceptions.StatementExecutionException;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class RunExample extends Command {
    private Controller ctr;
    public RunExample(String key, String desc,Controller ctr){
        super(key, desc);
        this.ctr=ctr;
    }
    @Override
    public void execute() {
        try{
            System.out.println("Display the steps?[Y/n]");
            Scanner read = new Scanner(System.in);
            //String option = "Y";
            String option =read.nextLine();
            if (Objects.equals(option, "Y"))
                ctr.setDisplayPrgStateFlag();

            ctr.allStep();
        }
        catch (ExpressionEvaluationException | ADTException | StatementExecutionException|InterpreterException | IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    } //here you must treat the exceptions that can not be solved in the controller
 }

