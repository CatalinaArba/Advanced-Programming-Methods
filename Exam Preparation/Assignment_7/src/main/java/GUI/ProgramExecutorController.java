package GUI;

import Controller.Controller;
import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.ProgramState.PrgState;
import Domain.Statement.IStmt;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.InterpreterException;
import Exceptions.StatementExecutionException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

class Pairs<T1,T2> {
    T1 first;
    T2 second;

    public Pairs(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }
}

public class ProgramExecutorController {
    private Controller controller;

    //all the items from fxml
    @FXML
    private TextField numberOfProgramStatesTextField;
    @FXML
    private TableView<Pairs<Integer, Value>> heapTableView;
    @FXML
    private TableColumn<Pairs<Integer, Value>, Integer> addressColumn;
    @FXML
    private TableColumn<Pairs<Integer, Value>, String> valueColumn;
    @FXML
    private ListView<String> outputListView;
    @FXML
    private ListView<String> fileTableListView;
    @FXML
    private ListView<Integer> programStateIdentifiersListView;
    @FXML
    private TableView<Pairs<String, Value>> symbolTableView;
    @FXML
    private TableColumn<Pairs<String, Value>, String> variableNameColumn;
    @FXML
    private TableColumn<Pairs<String, Value>, String> variableValueColumn;
    @FXML
    private ListView<String> executionStackListView;
    @FXML
    private Button runOneStepButton;


    public void initialize()
    {
        programStateIdentifiersListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        addressColumn.setCellValueFactory(p-> new SimpleIntegerProperty(p.getValue().first).asObject());
        valueColumn.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().second.toString()));
        variableNameColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().first));
        variableValueColumn.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().second.toString()));

    }



    public void setController(Controller controller){
        this.controller=controller;
        populate();
    }

    public void changePrgState(MouseEvent event)
    {
        populateExecutionStackListView();
        populateSymbolTavleView();
    }
    public void populate(){
        populateHeapTableView();
        populateOutputListView();
        populateFileTableListView();
        populateProgramStateIdentifiersListView();;
        populateSymbolTavleView();
        populateExecutionStackListView();
    }

    private PrgState getCurrentProgramState(){
        if(controller.getProgramStates().size()==0)
            return null;
        int currentId=programStateIdentifiersListView.getSelectionModel().getSelectedIndex();
        if(currentId==-1)
            return controller.getProgramStates().get(0);
        return controller.getProgramStates().get(currentId);
    }

    private void populateNumberOfProgramStatesTextField(){
        List<PrgState> programStates=controller.getProgramStates();
        numberOfProgramStatesTextField.setText(String.valueOf((programStates.size())));
    }

    private void populateHeapTableView(){
        PrgState programState=getCurrentProgramState();
        MyIHeap heap= Objects.requireNonNull(programState).getHeap();
        ArrayList<Pairs<Integer,Value>> heapEntries=new ArrayList<>();
        for(Map.Entry<Integer,Value> entry: heap.getContent().entrySet()) {
            heapEntries.add(new Pairs<>(entry.getKey(), entry.getValue()));
        }
        heapTableView.setItems(FXCollections.observableArrayList(heapEntries));
    }

    private void populateFileTableListView(){
        PrgState programState=getCurrentProgramState();
        List<String>files=new ArrayList<>(Objects.requireNonNull(programState).getFileTable().getContent().keySet());

        fileTableListView.setItems(FXCollections.observableList(files));
    }

    private void populateProgramStateIdentifiersListView(){
        List<PrgState> programStates=controller.getProgramStates();
        List<Integer>idList=programStates.stream().map(PrgState::getId).collect(Collectors.toList());
        programStateIdentifiersListView.setItems(FXCollections.observableList(idList));
        populateNumberOfProgramStatesTextField();
    }

    private void populateSymbolTavleView(){
        PrgState programState=getCurrentProgramState();
        MyIDictionary<String,Value>symbolTable=Objects.requireNonNull(programState).getSymTable();
        ArrayList<Pairs<String,Value>> symbolTableEntries=new ArrayList<>();
        for(Map.Entry<String,Value>entry:symbolTable.getContent().entrySet()){
            symbolTableEntries.add(new Pairs<>(entry.getKey(), entry.getValue()));
        }
        symbolTableView.setItems(FXCollections.observableArrayList(symbolTableEntries));
    }

    private void populateExecutionStackListView(){
        PrgState programState=getCurrentProgramState();
        List<String> executionStackToString=new ArrayList<>();
        if(programState!=null)
            for(IStmt statement:programState.getStk().getReversed()){
                executionStackToString.add(statement.toString());
            }
        executionStackListView.setItems(FXCollections.observableList(executionStackToString));
    }

    private void populateOutputListView()
    {
        PrgState prgStates = getCurrentProgramState();
        List<String> output = new ArrayList<>();
        List<Value> outputList = Objects.requireNonNull(prgStates).getOut().getList();
        int index;
        for(index =0; index<outputList.size(); index++)
        {
            output.add(outputList.get(index).toString());
        }
        outputListView.setItems(FXCollections.observableList(output));

    }

    public void runOneStep(MouseEvent mouseEv)
    {
        if (controller!=null)
        {
            try
            {
                List<PrgState> prgStates= Objects.requireNonNull(controller.getProgramStates());
                if(prgStates.size()>0)
                {
                    controller.oneStep();
                    populate();
                    prgStates=controller.removeCompletedPrg(controller.getProgramStates());
                    controller.setProgramStates(prgStates);
                    populateProgramStateIdentifiersListView();
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("An error has occured!");
                    alert.setContentText("There is nothing left to execute!");
                    alert.showAndWait();
                }
            } catch(InterpreterException | ExpressionEvaluationException| ADTException| StatementExecutionException| IOException| InterruptedException  e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Execution error!");
                alert.setHeaderText("An execution error has occured!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("An error has occured!");
            alert.setContentText("No program selected!");
            alert.showAndWait();
        }

    }
}
