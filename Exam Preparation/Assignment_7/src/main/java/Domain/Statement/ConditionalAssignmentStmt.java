package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIStack;
import Domain.Expression.Exp;
import Domain.Expression.ValueExp;
import Domain.Expression.VarExp;
import Domain.ProgramState.PrgState;
import Domain.Type.BoolType;
import Domain.Type.Type;
import Exceptions.*;

public class ConditionalAssignmentStmt implements IStmt{
    private final String variable;
    private final Exp expression1;
    private final Exp expression2;
    private final Exp expression3;

    public ConditionalAssignmentStmt(String variable, Exp expression1,Exp expression2,Exp expression3){
        this.variable=variable;
        this.expression1=expression1;
        this.expression2=expression2;
        this.expression3=expression3;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionEvaluationException, StatementExecutionException {
        MyIStack<IStmt> executionStack=state.getStk();
        IStmt transformed=new IfStmt(expression1,new AssignStmt(variable,expression2),new AssignStmt(variable,expression3));
        executionStack.push(transformed);
        state.setExeStack(executionStack);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException, StatementExecutionException, ExpressionEvaluationException, ADTException {
        Type expression1Type = expression1.typeCheck(typeEnv);
        Type expression2Type = expression2.typeCheck(typeEnv);
        Type expression3Type = expression3.typeCheck(typeEnv);
        Type variableType = new VarExp(variable).typeCheck(typeEnv);
        if (expression1Type.equals(new BoolType()) && expression2Type.equals(variableType) && expression3Type.equals(variableType))
            return typeEnv;
        else
            throw new InterpreterException("The conditional assignment is invalid!");
    }
    @Override
    public String toString() {
        return String.format("%s=(%s)? %s: %s", variable, expression1, expression2, expression3);
    }
}
