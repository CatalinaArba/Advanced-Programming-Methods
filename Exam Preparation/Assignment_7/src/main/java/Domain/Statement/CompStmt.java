package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIStack;
import Domain.ProgramState.PrgState;
import Domain.Type.Type;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.InterpreterException;
import Exceptions.StatementExecutionException;

public class CompStmt implements IStmt {
    IStmt first;
    IStmt snd;
    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.snd = second;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException,StatementExecutionException, ExpressionEvaluationException, ADTException {
        return snd.typeCheck(first.typeCheck(typeEnv));
    }

    @Override
    public String toString() {

        return "(" + first.toString() + ";" + snd.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) {
        MyIStack<IStmt> stk = state.getStk();
        stk.push(snd);
        stk.push(first);
        state.setExeStack(stk);
        return null;
    }
}

