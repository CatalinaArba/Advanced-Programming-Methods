package Controller;

import Domain.ProgramState.PrgState;
import Domain.Value.RefValue;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.InterpreterException;
import Exceptions.StatementExecutionException;
import Repository.MyIRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Pair {
    final PrgState first;
    final InterpreterException second;

    public Pair(PrgState first, InterpreterException second) {
        this.first = first;
        this.second = second;
    }
}
public class Controller {
    MyIRepository repository;
    boolean displayPrgStateFlag=false;
    ExecutorService executorService;

    public Controller(MyIRepository repository){

        this.repository=repository;
    }
    public void oneStep() throws InterpreterException,ExpressionEvaluationException,ADTException,StatementExecutionException, IOException, InterruptedException
    {
        executorService=Executors.newFixedThreadPool(2);
        List<PrgState> prgStates = removeCompletedPrg(repository.getPrgList());
        oneStepForAllPrograms(prgStates);
        conservativeGarbageCollector(prgStates);
        executorService.shutdownNow();
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


//    public PrgState oneStep(PrgState state) throws ADTException, StatementExecutionException, ExpressionEvaluationException {
//        MyIStack<IStmt> stk=state.getStk();
//        if(stk.isEmpty())
//            throw  new StatementExecutionException("Error:Controller: prgstate stack is empty");
//        IStmt  crtStmt = stk.pop();
//        state.setExeStack(stk);
//        return crtStmt.execute(state);
//    }

    public void allStep() throws InterruptedException, ExpressionEvaluationException, ADTException, StatementExecutionException, IOException, InterpreterException {
        executorService = Executors.newFixedThreadPool(2);
        List<PrgState> programStates = removeCompletedPrg(repository.getPrgList());
        while (programStates.size() > 0) {
            conservativeGarbageCollector(programStates);
            oneStepForAllPrograms(programStates);
            programStates = removeCompletedPrg(repository.getPrgList());
        }
        executorService.shutdownNow();
        repository.setPrgList(programStates);
    }
    private void displayPrgState(){
        if(displayPrgStateFlag)
            System.out.println(repository.toString());
    }



    public void setDisplayPrgStateFlag(){
        displayPrgStateFlag=true;
    }

    //A5
    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream().filter(p -> !p.isNotCompleted()).collect(Collectors.toList());
    }

    public void oneStepForAllPrograms(List<PrgState> programStates) throws InterpreterException, InterruptedException ,ExpressionEvaluationException,ADTException,StatementExecutionException,IOException{
        programStates.forEach(programState -> {
            try {
                repository.logPrgStateExec(programState);
                display(programState);
            } catch (IOException|ADTException  e) {
                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
            }
        });
        List<Callable<PrgState>> callList = programStates.stream()
                .map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                .collect(Collectors.toList());

        List<Pair> newProgramList;
        newProgramList = executorService.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return new Pair(future.get(), null);
                    } catch (ExecutionException | InterruptedException e) {
                        if (e.getCause() instanceof InterpreterException)
                            return new Pair(null, (InterpreterException) e.getCause());
                        System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
                        return null;
                    }
                }).filter(Objects::nonNull)
                .filter(pair -> pair.first != null || pair.second != null)
                .collect(Collectors.toList());

        for (Pair error: newProgramList)
            if (error.second != null)
                throw error.second;
        programStates.addAll(newProgramList.stream().map(pair -> pair.first).collect(Collectors.toList()));

        programStates.forEach(programState -> {
            try {
                repository.logPrgStateExec(programState);
            } catch (IOException|ADTException  e) {
                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
            }
        });
        repository.setPrgList(programStates);
    }

    //A5-display the program state if the flag is true
    private void display(PrgState prgState){
        if(displayPrgStateFlag)
        {
            System.out.println(prgState.toString());
        }
    }

    public void conservativeGarbageCollector(List<PrgState> programStates) {
        List<Integer> symTableAddresses = Objects.requireNonNull(programStates.stream()
                        .map(p -> getAddrFromSymTable(p.getSymTable().values()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                .collect(Collectors.toList());
        programStates.forEach(p -> {
            p.getHeap().setHeap((HashMap<Integer, Value>) safeGarbageCollector(symTableAddresses, getAddrFromHeap(p.getHeap().getContent().values()), p.getHeap().getContent()));
        });
    }
    public void setProgramStates(List<PrgState> programStates) {
        this.repository.setPrgList(programStates);
    }
    public List<PrgState> getProgramStates() {
        return this.repository.getPrgList();
    }
}

