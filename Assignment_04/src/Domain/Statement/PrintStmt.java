package Domain.Statement;

import Domain.ADT.MyIList;
import Domain.Expression.Exp;
import Domain.ProgramState.PrgState;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;

public class PrintStmt implements IStmt {
    Exp exp;

    public PrintStmt(Exp newExpression) {
        exp = newExpression;
    }

    @Override
    public String toString() {

        return String.format("print(" + exp.toString() + ")");
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionEvaluationException {
        MyIList<Value> out = state.getOut();
        out.add(exp.eval(state.getSymTable(), state.getHeap()));
        state.setOut(out);
        return state;
    }

}
