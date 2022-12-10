package Controller;

import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;
import Repository.MyIRepository;
import Domain.ProgramState.PrgState;
import Domain.Statement.IStmt;
import Domain.ADT.MyIStack;
import Domain.Value.RefValue;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {
    MyIRepository repository;
    boolean displayPrgStateFlag=false;

    public Controller(MyIRepository repository){

        this.repository=repository;
    }

    public List<Integer> getAddrFromHeap(Collection<Value> heapValues) {
        return heapValues.stream().filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> ( symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v;
                    return v1.getAddress();})
                .collect(Collectors.toList());
    }


    public PrgState oneStep(PrgState state) throws ADTException, StatementExecutionException, ExpressionEvaluationException {
        MyIStack<IStmt> stk=state.getStk();
        if(stk.isEmpty())
            throw  new StatementExecutionException("Error:Controller: prgstate stack is empty");
        IStmt  crtStmt = stk.pop();
        state.setExeStack(stk);
        return crtStmt.execute(state);
    }

    public void allStep()throws ADTException, StatementExecutionException,ExpressionEvaluationException {
        PrgState prg = repository.getCrtPrg();
        // repo is the controller field of type MyRepoInterface
        // here you can display the prg state
        repository.logPrgStateExec();
        while (!prg.getStk().isEmpty()){
            oneStep(prg);
            //here you can display the prg state
            repository.logPrgStateExec();
            prg.getHeap().setHeap((HashMap<Integer,Value>)safeGarbageCollector(
                    getAddrFromSymTable(prg.getSymTable().getContent().values()),
                            getAddrFromHeap(prg.getHeap().getContent().values()),
                            prg.getHeap().getContent()));
            repository.logPrgStateExec();
            displayPrgState();
        }
    }
    private void displayPrgState(){
        if(displayPrgStateFlag)
            System.out.println(repository.getCrtPrg().toString());
    }



    public void setDisplayPrgStateFlag(){
        displayPrgStateFlag=true;
    }

}

