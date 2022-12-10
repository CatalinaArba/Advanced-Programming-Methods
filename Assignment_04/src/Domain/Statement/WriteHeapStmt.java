package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Expression.Exp;
import Domain.ProgramState.PrgState;
import Domain.Value.RefValue;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;

public class WriteHeapStmt implements IStmt {
    private final String varName;
    private final Exp expression;

    public WriteHeapStmt(String newVarName, Exp newExpression) {
        varName = newVarName;
        expression = newExpression;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionEvaluationException, StatementExecutionException {
        //first we check if var_name is a variable defined in SymTable, if its type is a Ref type and if the address from the RefValue associated in SymTable is a key in Heap. If not, the execution is stopped with an appropriate error message.
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if (!symTable.isDefined(varName))
            throw new StatementExecutionException("Error: WriteHeapStmt: the variable " + varName + " is not defined in symTable");
        Value value = symTable.lookUp(varName);
        if (!(value instanceof RefValue refValue))
            throw new StatementExecutionException("Error: WriteHeapStmt: the variable " + varName + " is not refType");
        //Second the expression is evaluated and the result must have its type equal to the locationType of the var_name type. If not, the execution is stopped with an appropriate message.
        MyIHeap heap = state.getHeap();
        Value evalValue = expression.eval(symTable, heap);
        if (!evalValue.getType().equals(refValue.getLocationType()))
            throw new StatementExecutionException("Error: WriteHeapStmt:" + evalValue + " is not " + refValue.getLocationType());
        heap.update(refValue.getAddress(), evalValue);
        state.setHeap(heap);
        return state;


    }

    @Override
    public String toString() {
        return String.format("WriteHeap(%s, %s)", varName, expression);
    }
}
