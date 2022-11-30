package Domain.Statement;

import Domain.ADT.MyIList;
import Domain.Statement.IStmt;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Domain.ProgramState.PrgState;
import Domain.Expression.Exp;
import Domain.Value.*;

public class PrintStmt implements IStmt {
      Exp exp;

    public PrintStmt(Exp newExpression){
        exp=newExpression;
    }

    @Override
    public String toString(){

        return String.format("print(" +exp.toString()+")");
    }
    @Override
    public PrgState execute(PrgState state) throws ADTException,ExpressionEvaluationException{
        MyIList<Value> out=state.getOut();
        out.add(exp.eval(state.getSymTable()));
        state.setOut(out);
        return state;
      }

}
