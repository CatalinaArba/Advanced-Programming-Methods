package Domain.Statement;

import Domain.ADT.MyIStack;
import Domain.Expression.Exp;
import Domain.ProgramState.PrgState;
import Domain.Type.BoolType;
import Domain.Value.BoolValue;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;

public class WhileStmt implements IStmt {
    private final Exp expression;
    private final IStmt statement;

    public WhileStmt(Exp newExpression, IStmt newStatement) {
        expression = newExpression;
        statement = newStatement;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionEvaluationException, StatementExecutionException {
        Value value = expression.eval(state.getSymTable(), state.getHeap());
        MyIStack<IStmt> stack = state.getStk();
        if (!(value.getType().equals(new BoolType())))
            throw new StatementExecutionException("Error: WhileStmt: " + value + " is not a boolType!");
        BoolValue boolValue = (BoolValue) value;
        if (boolValue.getVal()) {
            stack.push(this);
            stack.push(statement);
        }
        return state;
    }

    @Override
    public String toString() {
        return String.format("while(%s){%s}", expression, statement);
    }


}
