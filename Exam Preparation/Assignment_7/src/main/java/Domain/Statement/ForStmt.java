package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIStack;
import Domain.Expression.Exp;
import Domain.Expression.RelationalExp;
import Domain.Expression.VarExp;
import Domain.ProgramState.PrgState;
import Domain.Type.IntType;
import Domain.Type.Type;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.InterpreterException;
import Exceptions.StatementExecutionException;

public class ForStmt implements  IStmt{
    private final String variable;
    private final Exp expression1;
    private final Exp expression2;
    private final Exp expression3;
    private final IStmt statement;

    public ForStmt (String variable, Exp expression1,Exp expression2,Exp expression3,IStmt statement){
        this.variable=variable;
        this.expression1=expression1;
        this.expression2=expression2;
        this.expression3=expression3;
        this.statement=statement;
    }
    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionEvaluationException, StatementExecutionException {
        MyIStack<IStmt> executionStack=state.getStk();
        IStmt transformed=new CompStmt(new AssignStmt("v", expression1),
                new WhileStmt(new RelationalExp("<", new VarExp("v"), expression2),
                        new CompStmt(statement, new AssignStmt("v", expression3))));
        executionStack.push(transformed);
        state.setExeStack(executionStack);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException,StatementExecutionException, ExpressionEvaluationException, ADTException {
        Type typeExp1= expression1.typeCheck(typeEnv);
        Type typeExp2= expression2.typeCheck(typeEnv);
        Type typeExp3= expression3.typeCheck(typeEnv);
        if(typeExp1.equals(new IntType())&&typeExp2.equals(new IntType())&&typeExp3.equals(new IntType()))
            return typeEnv;
        else
            throw new InterpreterException("For statement:Type check: error!");
    }

    @Override
    public String toString() {
        return String.format("for(%s=%s; %s<%s; %s=%s) {%s}", variable, expression1, variable, expression2, variable, expression3, statement);

    }
}

