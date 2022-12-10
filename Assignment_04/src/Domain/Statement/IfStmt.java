package Domain.Statement;

import Domain.ADT.MyIStack;
import Domain.Expression.Exp;
import Domain.ProgramState.PrgState;
import Domain.Value.BoolValue;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;

public class IfStmt implements IStmt {
    Exp exp;
    IStmt thenS;
    IStmt elseS;
    ///....

    public IfStmt(Exp e, IStmt t, IStmt el) {
        exp = e;
        thenS = t;
        elseS = el;
    }

    @Override
    public String toString() {
        return "(IF(" + exp.toString() + ") THEN(" + thenS.toString() + ")ELSE(" + elseS.toString() + "))";
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, StatementExecutionException, ExpressionEvaluationException {
        Value result = exp.eval(state.getSymTable(), state.getHeap());
        if (result instanceof BoolValue boolResult) {
            IStmt stmt;
            if (boolResult.getVal()) {
                stmt = thenS;
            } else {
                stmt = elseS;
            }
            MyIStack<IStmt> stack = state.getStk();
            stack.push(stmt);
            state.setExeStack(stack);
            return state;
        } else {
            throw new StatementExecutionException("Please provide a boolean expression in an if statement");
        }
    }

}
