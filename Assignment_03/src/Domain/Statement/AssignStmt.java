package Domain.Statement;
import Domain.Expression.*;
import Domain.ProgramState.*;
import Domain.ADT.*;
import Exceptions.*;
import Domain.Type.*;
import Domain.Value.*;
public class AssignStmt implements IStmt{
    String id;
    Exp exp;
    //....
    public AssignStmt(String key, Exp expression)
    {
        id=key;
        exp=expression;
    }
    @Override
    public String toString(){
        return id+"="+ exp.toString();}

    @Override
    public PrgState execute(PrgState state) throws ExpressionEvaluationException,ADTException, StatementExecutionException{
        MyIStack<IStmt> stk=state.getStk();
        MyIDictionary<String,Value> symTbl= state.getSymTable();

        if (symTbl.isDefined(id)){
            Value val = exp.eval(symTbl);
            Type typId= (symTbl.lookUp(id)).getType();
            if (val.getType(). equals(typId)   )
                symTbl.update(id, val);
             else throw new StatementExecutionException("declared type of variable"+id+" and type of  the assigned expression do not match");
        }
        else throw new StatementExecutionException("the used variable" +id + " was not declared before");
        state.setSymTable(symTbl);
        return state;
    }

}
