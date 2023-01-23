package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.Expression.Exp;
import Domain.ProgramState.PrgState;
import Domain.Type.BoolType;
import Domain.Type.Type;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.InterpreterException;
import Exceptions.StatementExecutionException;

public class DoWhileStmt implements IStmt{
    private final IStmt statement;
    private final Exp expression;

    public DoWhileStmt(Exp expression,IStmt statement){
        this.statement=statement;
        this.expression=expression;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionEvaluationException, StatementExecutionException {
        IStmt transformed=new CompStmt(statement,new WhileStmt(expression,statement));
        state.getStk().push(transformed);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException, StatementExecutionException, ExpressionEvaluationException, ADTException {
        Type expressionType= expression.typeCheck(typeEnv);
        if(expressionType.equals(new BoolType())){
            statement.typeCheck(typeEnv.copy());
            return typeEnv;
        }
        else
            throw new InterpreterException("Condition in the do while statement must be bool!");

    }

    @Override
    public String toString() {
        return String.format("do {%s} while (%s)", statement, expression);
    }
}
