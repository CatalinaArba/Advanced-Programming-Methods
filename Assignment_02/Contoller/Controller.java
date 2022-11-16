package Contoller;

import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;
import Repository.MyIRepository;
import Domain.ProgramState.PrgState;
import Domain.Statement.IStmt;
import Domain.ADT.MyIStack;

public class Controller {
    MyIRepository repository;
    boolean displayPrgStateFlag=false;

    public Controller(MyIRepository repository){

        this.repository=repository;
    }

    public PrgState oneStep(PrgState state) throws ADTException, StatementExecutionException, ExpressionEvaluationException {
        MyIStack<IStmt> stk=state.getStk();
        if(stk.isEmpty())
            throw  new StatementExecutionException("Error:Controller: prgstate stack is empty");
        IStmt  crtStmt = stk.pop();
        state.setExeStack(stk);
        return crtStmt.execute(state);
    }

    public void allStep()throws ADTException, StatementExecutionException,ExpressionEvaluationException{
        PrgState prg = repository.getCrtPrg();
        // repo is the controller field of type MyRepoInterface
        // here you can display the prg state
        displayPrgState();
        while (!prg.getStk().isEmpty()){
            oneStep(prg);
            //here you can display the prg state
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

