package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.ADT.MyIStack;
import Domain.Expression.Exp;
import Domain.ProgramState.*;
import Domain.Type.Type;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;

public class AssignStmt implements IStmt{
    String id;
    Exp exp;

    public AssignStmt(String key, Exp expression)
    {
        id=key;
        exp=expression;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementExecutionException, ExpressionEvaluationException, ADTException {
        Type typeVar = typeEnv.lookUp(id);
        Type typeExpr = exp.typeCheck(typeEnv);
        if (typeVar.equals(typeExpr))
            return typeEnv;
        else
            throw new StatementExecutionException("Assignment: right hand side and left hand side have different types.");
    }

    @Override
    public String toString(){
        return id+"="+ exp.toString();}

    @Override
    public PrgState execute(PrgState state) throws ExpressionEvaluationException,ADTException, StatementExecutionException{
        MyIStack<IStmt> stk=state.getStk();
        MyIDictionary<String, Value> symTbl= state.getSymTable();
        MyIHeap heap = state.getHeap();
        if (symTbl.isDefined(id)){
            Value val = exp.eval(symTbl,heap);
            Type typId= (symTbl.lookUp(id)).getType();
            if (val.getType(). equals(typId)   )
                symTbl.update(id, val);
             else throw new StatementExecutionException("declared type of variable"+id+" and type of  the assigned expression do not match");
        }
        else throw new StatementExecutionException("the used variable" +id + " was not declared before");
        state.setSymTable(symTbl);
        return null;
    }

}
