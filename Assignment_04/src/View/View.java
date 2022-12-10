package View;
import Controller.Controller;
import Domain.ADT.*;
import Domain.Statement.*;
import Domain.Type.*;
import Domain.Value.*;
import java.util.Objects;

import java.util.Scanner;
import Domain.Expression.*;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;
import Domain.ProgramState.PrgState;
import Repository.MyIRepository;
import Repository.MyRepository;
import java.io.BufferedReader;

public class View {
    public void startMenu() {
        boolean continueLoop = true;
        printManu();
        while (continueLoop) {
            try {
                Scanner read = new Scanner(System.in);
                int option = read.nextInt();
                switch (option) {
                    case 0:
                        continueLoop = false;
                        break;
                    case 1:
                        runExample1();
                        break;
                    case 2:
                        runExample2();
                        break;
                    case 3:
                        runExample3();
                        break;
                    default:
                        System.out.println("Wrong command! Try again!");


                }

            } catch (Exception exception) {
                System.out.println("\u001B[31m" + exception.getMessage() + "\u001B[0m");
            }
        }
    }

    private void runExample1() throws ExpressionEvaluationException, ADTException, StatementExecutionException {
        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
        runExample(ex1);

    }



    private void runExample2() throws ExpressionEvaluationException, ADTException, StatementExecutionException {
        IStmt ex2 = new CompStmt(
                new VarDeclStmt("a",new IntType()),
                new CompStmt(
                        new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a",
                                new ArithExp('+',
                                        new ValueExp(
                                                new IntValue(2)),
                                        new ArithExp('*',
                                                new ValueExp(
                                                        new IntValue(3)),
                                                new ValueExp(
                                                        new IntValue(5))))),
                                new CompStmt(
                                        new AssignStmt("b",
                                                new ArithExp('+',
                                                        new VarExp("a"),
                                                        new ValueExp(new IntValue(1)))),
                                        new PrintStmt(new VarExp("b"))))));

        runExample(ex2);

    }
    private void runExample3() throws ExpressionEvaluationException, ADTException, StatementExecutionException {
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a",
                                new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),
                                        new AssignStmt("v",new ValueExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));
        runExample(ex3);
    }
    private void runExample(IStmt stmt) throws ExpressionEvaluationException,ADTException,StatementExecutionException{
        MyIStack<IStmt> execStack=new MyStack<>();
        MyIDictionary<String,Value> symTable=new MyDictionary<>();
        MyIList<Value> output=new MyList<>();
        MyIDictionary<String, BufferedReader> fileTable = new MyDictionary<>();

        PrgState prgState=new PrgState(execStack,symTable,output,fileTable,new MyHeap(),stmt);
        MyIRepository repository=new MyRepository(prgState,"log.txt");
        Controller controller=new Controller(repository);

        System.out.println("Display the steps?[Y/n]");
        //Scanner read = new Scanner(System.in);
        String option = "Y";
                //read.nextLine();
        if (Objects.equals(option, "Y"))
            controller.setDisplayPrgStateFlag();

        controller.allStep();
        System.out.println("Result is" + prgState.getOut().toString().replace('[', ' ').replace(']', ' '));
    }

    private void printManu(){
        System.out.println("MENU: ");
        System.out.println("0. Exit.");
        System.out.println("1. Run the first program: \n\tint v;\n\tv=2;\n\tPrint(v)");
        System.out.println("2. Run the second program: \n\tint a;\n\tint b;\n\ta=2+3*5;\n\tb=a+1;\n\tPrint(b)");
        System.out.println("3. Run the third program: \n\tbool a;\n\tint v;\n\ta=true;\n\t(If a Then v=2 Else v=3);\n\tPrint(v)");


        System.out.println("Choose an option: ");
    }
}
