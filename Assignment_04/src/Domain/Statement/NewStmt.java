package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Expression.Exp;
import Domain.ProgramState.PrgState;
import Domain.Type.RefType;
import Domain.Type.Type;
import Domain.Value.RefValue;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;

public class NewStmt implements IStmt {
    private final String varName;
    private final Exp expression;

    public NewStmt(String varName, Exp expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionEvaluationException, StatementExecutionException {
        //check whether var_name is a variable in SymTable and its type is a RefType. If not, the execution is stopped with an appropriate error message.
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if (!symTable.isDefined(varName))
            throw new StatementExecutionException("Error: NewStmt:" + varName + " is not in symTable!");
        Value varValue = symTable.lookUp(varName);
        if (!(varValue.getType() instanceof RefType))
            throw new StatementExecutionException("Error: NewStmt:" + varName + " is not in refType!");
        //Evaluate the expression to a value and then compare the type of the value to the locationType from the value associated to var_name in SymTable. If the types are not equal, the execution is stopped with an appropriate error message.
        Value evalVal = expression.eval(symTable, state.getHeap());
        Type evalType = evalVal.getType();
        Type locationType = ((RefValue) varValue).getLocationType();
        if (!locationType.equals(evalType))
            throw new StatementExecutionException("Error: NewStmt:" + varName + " is not evaluated correctly!");
        //Create a new entry in the Heap table such that a new key (new free address) is generated and it is associated to the result of the expression evaluation
        MyIHeap heap = state.getHeap();
        int newPosition = heap.add(evalVal);
        //in SymTable update the RefValue associated to the var_name such that the new RefValue has the same locationType and the address is equal to the new key generated in the Heap at the previous step
        symTable.put(varName, new RefValue(newPosition, locationType));
        state.setSymTable(symTable);
        state.setHeap(heap);
        return state;

    }

    @Override
    public String toString() {
        return String.format("New(%s, %s)", varName, expression);
    }
}
