package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIStack;
import Domain.Expression.Exp;
import Domain.Expression.RelationalExp;
import Domain.ProgramState.PrgState;
import Domain.Type.Type;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.InterpreterException;
import Exceptions.StatementExecutionException;

public class SwitchStmt implements IStmt{
    private final Exp mainExpression;
    private final Exp expression1;
    private final IStmt statement1;
    private final Exp expression2;
    private final IStmt statement2;
    private final IStmt defaultStatement;

    public SwitchStmt(Exp me, Exp e1, IStmt s1,Exp e2, IStmt s2,IStmt ds)
    {
        this.mainExpression=me;
        this.expression1=e1;
        this.statement1=s1;
        this.expression2=e2;
        this.statement2=s2;
        this.defaultStatement=ds;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionEvaluationException, StatementExecutionException {
        MyIStack<IStmt>executionStack=state.getStk();
        IStmt transformed=new IfStmt(new RelationalExp("==",mainExpression,expression1),statement1,new IfStmt(new RelationalExp("==",mainExpression,expression2),statement2,defaultStatement));
        executionStack.push(transformed);
        state.setExeStack(executionStack);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException, StatementExecutionException, ExpressionEvaluationException, ADTException {
        Type mainType= mainExpression.typeCheck(typeEnv);
        Type expression1Type=expression1.typeCheck(typeEnv);
        Type expression2Type=expression2.typeCheck(typeEnv);
        if(mainType.equals(expression1Type)&&mainType.equals(expression2Type)){
            statement1.typeCheck(typeEnv.copy());
            statement2.typeCheck(typeEnv.copy());
            defaultStatement.typeCheck(typeEnv.copy());
            return typeEnv;
        }
        else {
            throw new InterpreterException("The expression types don't match in the switch statement!");
        }
    }

    @Override
    public String toString() {
        return  String.format("switch(%s){(case(%s): %s)(case(%s): %s)(default: %s)}", mainExpression, expression1, statement1, expression2, statement2, defaultStatement);
    }
}
