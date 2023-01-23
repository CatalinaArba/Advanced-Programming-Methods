package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIStack;
import Domain.Expression.Exp;
import Domain.ProgramState.PrgState;
import Domain.Type.BoolType;
import Domain.Type.Type;
import Domain.Value.BoolValue;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.InterpreterException;
import Exceptions.StatementExecutionException;

public class WhileStmt implements IStmt {
    private final Exp expression;
    private final IStmt statement;

    public WhileStmt(Exp newExpression, IStmt newStatement) {
        expression = newExpression;
        statement = newStatement;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException, StatementExecutionException, ExpressionEvaluationException, ADTException {
        Type typeExpr = expression.typeCheck(typeEnv);
        if (typeExpr.equals(new BoolType())) {
            statement.typeCheck(typeEnv.copy());
            return typeEnv;
        } else
            throw new StatementExecutionException("The condition of WHILE does not have the type Bool.");
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
        return null;
    }

    @Override
    public String toString() {
        return String.format("while(%s){%s}", expression, statement);
    }


}
