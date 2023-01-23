package GUI;
import Controller.Controller;
import Domain.ADT.*;
import Domain.Expression.*;
import Domain.ProgramState.PrgState;
import Domain.Statement.*;
import Domain.Type.*;
import Domain.Value.BoolValue;
import Domain.Value.IntValue;
import Domain.Value.RefValue;
import Domain.Value.StringValue;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.InterpreterException;
import Exceptions.StatementExecutionException;
import Repository.MyIRepository;
import Repository.MyRepository;
import View.RunExample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProgramChooserController {
    private ProgramExecutorController programExecutorController;
    @FXML
    private ListView<IStmt> programsListView;

    @FXML
    private Button displayButton;

    @FXML
    public void initialize(){
        programsListView.setItems(getAllStatements());
        programsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void setProgramExecutorController(ProgramExecutorController programExecutorController){
        this.programExecutorController=programExecutorController;
    }

    @FXML
    private void displayProgram(MouseEvent actionEvent){
        IStmt selectedStatements=programsListView.getSelectionModel().getSelectedItem();
        if (selectedStatements==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error encountered!");
            alert.setContentText("No statement selected!");
            alert.showAndWait();
        }
        else{
            int id=programsListView.getSelectionModel().getSelectedIndex();
            try{
                //selectedStatements.typeCheck(new MyDictionary<>());
                selectedStatements.typeCheck(new MyDictionary<>());
                PrgState programState = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), selectedStatements);
                MyIRepository repository = new MyRepository(programState, "log" + (id + 1) + ".txt");
                Controller controller = new Controller(repository);
                programExecutorController.setController(controller);

            }catch (InterpreterException|ADTException |ExpressionEvaluationException | StatementExecutionException  exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error encountered!");
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private ObservableList<IStmt> getAllStatements(){
        List<IStmt> allStatements=new ArrayList<>();

        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));




        IStmt ex2 = new CompStmt(new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new
                                ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new
                                        IntValue(1)))), new PrintStmt(new VarExp("b"))))));



        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),
                                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFile(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFile(new VarExp("varf"))))))))));



        IStmt ex5 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(5))),
                                new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(7))),
                                        new IfStmt(new RelationalExp(">", new VarExp("a"),
                                                new VarExp("b")),new PrintStmt(new VarExp("a")),
                                                new PrintStmt(new VarExp("b")))))));



        //int v; v=4; (while (v>0) print(v);v=v-1);print(v)

        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v",new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));


        // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHeapExpression(new VarExp("v"))),
                                                new PrintStmt(new ArithExp('+',new ReadHeapExpression(new ReadHeapExpression(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));


        //Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt( new PrintStmt(new ReadHeapExpression(new VarExp("v"))),
                                new CompStmt(new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+', new ReadHeapExpression(new VarExp("v")), new ValueExp(new IntValue(5))))))));

        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExpression(new ReadHeapExpression(new VarExp("a")))))))));

        //int v; Ref int a; v=10;new(a,22);   fork(wH(a,30);v=32;print(v);print(rH(a)));   print(v);print(rH(a))
        IStmt ex11 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExpression(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExpression(new VarExp("a")))))))));



        //FOR STATEMENT
        //v=20;
        //(for(v=0;v<3;v=v+1) fork(print(v);v=v+1) );
        //print(v*10)
        //The final Out should be {0,1,2,30}
        IStmt ex12 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new ForStmt("v", new ValueExp(new IntValue(0)),
                                new ValueExp(new IntValue(3)),
                                new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1))),
                                new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1))))))),
                                new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10)))))));



        //SLEEP STATEMENT
        IStmt ex13 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(0))),
                        new CompStmt(new WhileStmt(new RelationalExp("<", new VarExp("v"), new ValueExp(new IntValue(3))),
                                new CompStmt(new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                        new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new CompStmt(new SleepStmt(5), new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10))))))));

        //WAIT STATEMENT
        IStmt ex14 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new WaitStmt(10),
                                new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10)))))));

        //SWITCH STATEMENT
        IStmt ex15 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new VarDeclStmt("c", new IntType()),
                                new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(1))),
                                        new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(2))),
                                                new CompStmt(new AssignStmt("c", new ValueExp(new IntValue(5))),
                                                        new CompStmt(new SwitchStmt(
                                                                new ArithExp('*', new VarExp("a"), new ValueExp(new IntValue(10))),
                                                                new ArithExp('*', new VarExp("b"), new VarExp("c")),
                                                                new CompStmt(new PrintStmt(new VarExp("a")), new PrintStmt(new VarExp("b"))),
                                                                new ValueExp(new IntValue(10)),
                                                                new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))), new PrintStmt(new ValueExp(new IntValue(200)))),
                                                                new PrintStmt(new ValueExp(new IntValue(300)))
                                                        ), new PrintStmt(new ValueExp(new IntValue(300))))))))));

        //REPEAT UNTIL STATEMENT
        IStmt ex16 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(0))),
                        new CompStmt(new RepeatUntilStmt(
                                new CompStmt(new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                        new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1))))),
                                new RelationalExp("==", new VarExp("v"), new ValueExp(new IntValue(3)))
                        ),
                                new CompStmt(new VarDeclStmt("x", new IntType()),
                                        new CompStmt(new VarDeclStmt("y", new IntType()),
                                                new CompStmt(new VarDeclStmt("z", new IntType()),
                                                        new CompStmt(new VarDeclStmt("w", new IntType()),
                                                                new CompStmt(new AssignStmt("x", new ValueExp(new IntValue(1))),
                                                                        new CompStmt(new AssignStmt("y", new ValueExp(new IntValue(2))),
                                                                                new CompStmt(new AssignStmt("z", new ValueExp(new IntValue(3))),
                                                                                        new CompStmt(new AssignStmt("w", new ValueExp(new IntValue(4))),
                                                                                                new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10)))))))))))))));

        //CONDITIONAL ASSIGNMENT
        IStmt ex17 = new CompStmt(new VarDeclStmt("b", new BoolType()),
                new CompStmt(new VarDeclStmt("c", new IntType()),
                        new CompStmt(new AssignStmt("b", new ValueExp(new BoolValue(true))),
                                new CompStmt(new ConditionalAssignmentStmt("c",
                                        new VarExp("b"),
                                        new ValueExp(new IntValue(100)),
                                        new ValueExp(new IntValue(200))),
                                        new CompStmt(new PrintStmt(new VarExp("c")),
                                                new CompStmt(new ConditionalAssignmentStmt("c",
                                                        new ValueExp(new BoolValue(false)),
                                                        new ValueExp(new IntValue(100)),
                                                        new ValueExp(new IntValue(200))),
                                                        new PrintStmt(new VarExp("c"))))))));
        //DO WHILE STATEMENT
        IStmt ex19 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new DoWhileStmt(new RelationalExp(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v",new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));

        //Ref int a; new(a,20);
        //(for(v=0;v<3;v=v+1) fork(print(v);v=v*rh(a)));
        //print(rh(a))
        //The final Out should be {0,1,2,20}
        IStmt ex20=new CompStmt(new VarDeclStmt("a",new RefType(new IntType())),
                new CompStmt(new NewStmt("a",new ValueExp(new IntValue(20))),
                        new CompStmt( new VarDeclStmt("v", new IntType()),
                                new CompStmt(new ForStmt("v",new ValueExp(new IntValue(0)),
                                        new ValueExp(new IntValue(3)),
                                        new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1))),
                                        new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                                                new AssignStmt("v",new ArithExp('*',new VarExp("v"),new ReadHeapExpression(new VarExp("a"))))))),
                                        new PrintStmt(new ReadHeapExpression(new VarExp("a")))))));

        allStatements.add(ex1);
        allStatements.add(ex2);
        allStatements.add(ex3);
        allStatements.add(ex4);
        allStatements.add(ex5);
        allStatements.add(ex6);
        allStatements.add(ex7);
        allStatements.add(ex8);
        allStatements.add(ex9);
        allStatements.add(ex10);
        allStatements.add(ex11);
        allStatements.add(ex12);
        allStatements.add(ex13);
        allStatements.add(ex14);
        allStatements.add(ex15);
        allStatements.add(ex16);
        allStatements.add(ex17);
        allStatements.add(ex19);
        allStatements.add(ex20);
        return FXCollections.observableArrayList(allStatements);
    }

}
