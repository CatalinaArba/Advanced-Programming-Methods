package View;

import Controller.Controller;
import Domain.ADT.MyDictionary;
import Domain.ADT.MyHeap;
import Domain.ADT.MyList;
import Domain.ADT.MyStack;
import Domain.Expression.*;
import Domain.ProgramState.PrgState;
import Domain.Statement.*;
import Domain.Type.BoolType;
import Domain.Type.IntType;
import Domain.Type.RefType;
import Domain.Type.StringType;
import Domain.Value.BoolValue;
import Domain.Value.IntValue;
import Domain.Value.StringValue;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.InterpreterException;
import Exceptions.StatementExecutionException;
import Repository.MyIRepository;
import Repository.MyRepository;

class Interpreter {
    public static void main(String[] args)
    {
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
            new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                    new PrintStmt(new VarExp("v"))));
        try{
        ex1.typeCheck(new MyDictionary<>());
        PrgState prg1 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),new MyHeap(), ex1);
        MyIRepository repo1 = new MyRepository(prg1, "log1.txt");
        Controller controller1 = new Controller(repo1);
        menu.addCommand(new RunExample("1",ex1.toString(),controller1));
        } catch (ExpressionEvaluationException | InterpreterException | ADTException | StatementExecutionException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex2 = new CompStmt(new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new
                                ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new
                                        IntValue(1)))), new PrintStmt(new VarExp("b"))))));

        try{
            ex2.typeCheck(new MyDictionary<>());
            PrgState prg2 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),new MyHeap(), ex2);
            MyIRepository repo2 = new MyRepository(prg2, "log2.txt");
            Controller controller2 = new Controller(repo2);
            menu.addCommand(new RunExample("2",ex2.toString(),controller2));
        }catch (ExpressionEvaluationException| InterpreterException  | ADTException | StatementExecutionException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),
                                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));
        try {
            ex3.typeCheck(new MyDictionary<>());
            PrgState prg3 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex3);
            MyIRepository repo3 = new MyRepository(prg3, "log3.txt");
            Controller controller3 = new Controller(repo3);
            menu.addCommand(new RunExample("3",ex3.toString(),controller3));
        }catch (ExpressionEvaluationException| InterpreterException  | ADTException | StatementExecutionException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFile(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFile(new VarExp("varf"))))))))));
    try {
        ex4.typeCheck(new MyDictionary<>());
        PrgState prg4 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex4);
        MyIRepository repo4 = new MyRepository(prg4, "log4.txt");
        Controller controller4 = new Controller(repo4);
        menu.addCommand(new RunExample("4",ex4.toString(),controller4));
    }catch (ExpressionEvaluationException | InterpreterException | ADTException | StatementExecutionException e) {
        System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
    }


        IStmt ex5 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(5))),
                                new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(7))),
                                        new IfStmt(new RelationalExp(">", new VarExp("a"),
                                                new VarExp("b")),new PrintStmt(new VarExp("a")),
                                                new PrintStmt(new VarExp("b")))))));
        try {
            ex5.typeCheck(new MyDictionary<>());
            PrgState prg5 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex5);
            MyIRepository repo5 = new MyRepository(prg5, "log5.txt");
            Controller controller5 = new Controller(repo5);
            menu.addCommand(new RunExample("5",ex5.toString(),controller5));
        }catch (ExpressionEvaluationException | InterpreterException | ADTException | StatementExecutionException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        //int v; v=4; (while (v>0) print(v);v=v-1);print(v)

        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v",new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));
        try{
        ex6.typeCheck(new MyDictionary<>());
        PrgState prg6 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),  new MyHeap(),ex6);
        MyIRepository repo6 = new MyRepository(prg6, "log6.txt");
        Controller controller6 = new Controller(repo6);
            menu.addCommand(new RunExample("6",ex6.toString(),controller6));
        }
        catch (ExpressionEvaluationException| InterpreterException  | ADTException | StatementExecutionException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));
        try {
            ex7.typeCheck(new MyDictionary<>());
            PrgState prg7 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex7);
            MyIRepository repo7 = new MyRepository(prg7, "log7.txt");
            Controller controller7 = new Controller(repo7);
            menu.addCommand(new RunExample("7",ex7.toString(),controller7));
        }catch (ExpressionEvaluationException| InterpreterException  | ADTException | StatementExecutionException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHeapExpression(new VarExp("v"))),
                                                new PrintStmt(new ArithExp('+',new ReadHeapExpression(new ReadHeapExpression(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));
        try {
            ex8.typeCheck(new MyDictionary<>());
            PrgState prg8 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex8);
            MyIRepository repo8 = new MyRepository(prg8, "log8.txt");
            Controller controller8 = new Controller(repo8);
            menu.addCommand(new RunExample("8",ex8.toString(),controller8));
        }
        catch (ExpressionEvaluationException| InterpreterException  | ADTException | StatementExecutionException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        //Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt( new PrintStmt(new ReadHeapExpression(new VarExp("v"))),
                                new CompStmt(new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+', new ReadHeapExpression(new VarExp("v")), new ValueExp(new IntValue(5))))))));
        try {
            ex9.typeCheck(new MyDictionary<>());
            PrgState prg9 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex9);
            MyIRepository repo9 = new MyRepository(prg9, "log9.txt");
            Controller controller9 = new Controller(repo9);
            menu.addCommand(new RunExample("9",ex9.toString(),controller9));
        }
        catch (ExpressionEvaluationException| InterpreterException  | ADTException | StatementExecutionException e) {
                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
            }


        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                         new PrintStmt(new ReadHeapExpression(new ReadHeapExpression(new VarExp("a")))))))));

        try{
            ex10.typeCheck(new MyDictionary<>());
            PrgState prg10 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex10);
            MyIRepository repo10;
            repo10 = new MyRepository(prg10, "log10.txt");
            Controller controller10 = new Controller(repo10);
            menu.addCommand(new RunExample("10",ex10.toString(),controller10));
        }
        catch (ExpressionEvaluationException| InterpreterException  | ADTException | StatementExecutionException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }
        //int v; Ref int a; v=10;new(a,22);   fork(wH(a,30);v=32;print(v);print(rH(a)));   print(v);print(rH(a))
        IStmt ex11 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExpression(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExpression(new VarExp("a")))))))));
        try {
            ex11.typeCheck(new MyDictionary<>());
            PrgState prg11 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex11);
            MyIRepository repo11;
            repo11 = new MyRepository(prg11, "log11.txt");
            Controller controller11 = new Controller(repo11);
            menu.addCommand(new RunExample("11",ex11.toString(),controller11));
        }
        catch (ExpressionEvaluationException| InterpreterException  | ADTException | StatementExecutionException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        menu.show();

    }


}
