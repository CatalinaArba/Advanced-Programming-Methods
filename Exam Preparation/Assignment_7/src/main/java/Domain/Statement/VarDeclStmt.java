package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.ProgramState.PrgState;
import Domain.Type.Type;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;

public class VarDeclStmt implements IStmt {
    String name;
    Type typ;

    public VarDeclStmt(String name, Type type) {
        this.name = name;
        this.typ = type;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementExecutionException, ExpressionEvaluationException, ADTException {
        typeEnv.put(name, typ);
        return typeEnv;
    }

    @Override
    public String toString() {
        return typ.toString() + ' ' + name;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionEvaluationException, StatementExecutionException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if (symTable.isDefined(name)) {
            throw new StatementExecutionException("Variable " + name + " already exists in the symTable.");
        }
        symTable.put(name, typ.defaultValue());
        state.setSymTable(symTable);
        return null;
    }
}