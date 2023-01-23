package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIList;
import Domain.Expression.Exp;
import Domain.ProgramState.PrgState;
import Domain.Type.Type;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;

public class PrintStmt implements IStmt {
    Exp exp;

    public PrintStmt(Exp newExpression) {
        exp = newExpression;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementExecutionException, ExpressionEvaluationException, ADTException {
        exp.typeCheck(typeEnv);
        return typeEnv;
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
        return null;
    }

}
