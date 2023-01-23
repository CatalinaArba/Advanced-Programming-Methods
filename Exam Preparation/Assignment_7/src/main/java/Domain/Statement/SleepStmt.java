package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIStack;
import Domain.ProgramState.PrgState;
import Domain.Type.Type;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.InterpreterException;
import Exceptions.StatementExecutionException;

public class SleepStmt implements IStmt{
    private final int value;

    public SleepStmt(int value){
        this.value=value;
    }
    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionEvaluationException, StatementExecutionException {
        if (value!=0){
            MyIStack<IStmt>executionStack=state.getStk();
            executionStack.push(new SleepStmt(value-1));
            state.setExeStack(executionStack);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException, StatementExecutionException, ExpressionEvaluationException, ADTException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return String.format("sleep(%s)", value);
    }
}
