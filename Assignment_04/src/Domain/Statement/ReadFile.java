package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.Expression.Exp;
import Domain.ProgramState.PrgState;
import Domain.Type.IntType;
import Domain.Type.StringType;
import Domain.Value.IntValue;
import Domain.Value.StringValue;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt {

    private final Exp expression;//path
    private final String variableName;//integer

    //Constructor
    public ReadFile(Exp expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionEvaluationException, StatementExecutionException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();

        //check whether var_name is defined in SymTable and its type is int.
        if (symTable.isDefined(variableName)) {
            Value value = symTable.lookUp(variableName);
            if (value.getType().equals(new IntType())) {

                //evaluate exp to a value that must be a string value.
                value = expression.eval(symTable, state.getHeap());
                if (value.getType().equals(new StringType())) {

                    //using the previous step value we get the BufferedReader object associated in the FileTable.
                    StringValue stringValue = (StringValue) value;
                    if (fileTable.isDefined(stringValue.getVal())) {
                        BufferedReader bufferReader = fileTable.lookUp(stringValue.getVal());
                        try {
                            String line = bufferReader.readLine();
                            if (line == null)
                                line = "0";
                            symTable.put(variableName, new IntValue(Integer.parseInt(line)));
                        } catch (IOException e) {
                            throw new StatementExecutionException(String.format("Could not read from file " + stringValue));
                        }
                    } else throw new StatementExecutionException("The file doesn't contain " + stringValue);
                } else throw new StatementExecutionException(value + " doesn't evaluate as a String Type");
            } else throw new StatementExecutionException(value + " doesn't evaluate as a Int Type");
        } else throw new StatementExecutionException(variableName + " isn't in SymTable");

        return state;
    }

    @Override
    public String toString() {
        return "readFile(" + expression.toString() + ")";
    }
}
