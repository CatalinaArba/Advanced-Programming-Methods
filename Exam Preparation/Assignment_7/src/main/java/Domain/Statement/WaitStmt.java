package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIStack;
import Domain.Expression.ValueExp;
import Domain.ProgramState.PrgState;
import Domain.Type.Type;
import Domain.Value.IntValue;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.InterpreterException;
import Exceptions.StatementExecutionException;

public class WaitStmt implements IStmt{
    private final int value;

    public WaitStmt(int value){
        this.value=value;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionEvaluationException, StatementExecutionException {
        if(value!=0){
            MyIStack<IStmt>executionStack= state.getStk();
            executionStack.push(new CompStmt(new PrintStmt(new ValueExp(new IntValue(value))),new WaitStmt(value-1)));
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
        return String.format("wait(%s)", value);
    }
}
