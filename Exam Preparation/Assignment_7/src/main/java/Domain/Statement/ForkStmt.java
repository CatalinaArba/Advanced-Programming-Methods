package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIStack;
import Domain.ADT.MyStack;
import Domain.ProgramState.PrgState;
import Domain.Type.Type;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.InterpreterException;
import Exceptions.StatementExecutionException;

public class ForkStmt implements IStmt{
    private IStmt statement;

    public ForkStmt(IStmt newStatement){
        statement=newStatement;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException, StatementExecutionException, ExpressionEvaluationException, ADTException {
        statement.typeCheck(typeEnv.copy());
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionEvaluationException, StatementExecutionException {
        MyIStack<IStmt> newStack=new MyStack<>();
        newStack.push(statement);
        return new PrgState(newStack, state.getSymTable().copy(),
                state.getOut(), state.getFileTable(), state.getHeap());
    }

    @Override
    public String toString() {
        return "Fork "+ statement.toString();
    }
}
