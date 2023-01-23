package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIStack;
import Domain.Expression.Exp;
import Domain.Expression.NotExp;
import Domain.ProgramState.PrgState;
import Domain.Type.BoolType;
import Domain.Type.Type;
import Exceptions.*;

public class RepeatUntilStmt implements IStmt{
    private final IStmt statement;
    private final Exp expression;

    public RepeatUntilStmt(IStmt statement, Exp expression){
        this.statement=statement;
        this.expression=expression;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionEvaluationException, StatementExecutionException {
        MyIStack<IStmt> executionStack=state.getStk();
        IStmt transformed=new CompStmt(statement,new WhileStmt(new NotExp(expression),statement));
        executionStack.push(transformed);
        state.setExeStack(executionStack);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException, StatementExecutionException, ExpressionEvaluationException, ADTException {
        Type type1=expression.typeCheck(typeEnv);
        if(type1.equals(new BoolType())){
            statement.typeCheck(typeEnv.copy());
            return typeEnv;
        }
        else {
            throw new InterpreterException("Expression in the repeat statement must be of Bool type!");
        }
    }

    @Override
    public String toString() {
        return String.format("repeat(%s) until (%s)", statement, expression);
    }
}
