package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.Expression.Exp;
import Domain.ProgramState.PrgState;
import Domain.Type.StringType;
import Domain.Value.StringValue;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt {
    private final Exp expression;

    public CloseRFile(Exp expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionEvaluationException, StatementExecutionException {
        //evaluate exp to a value that must be a string.
        Value value = expression.eval(state.getSymTable(), state.getHeap());
        if (value.getType().equals(new StringType())) {
            //Use the value (computed at the previous step) to get the entry into the FileTable and get the associated BufferedReader object.
            StringValue fileName = (StringValue) value;
            MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
            if (fileTable.isDefined(fileName.getVal())) {
                //call the close method of the BufferedReader object and delete the entry from the FileTable
                BufferedReader br = fileTable.lookUp(fileName.getVal());
                try {
                    br.close();
                } catch (IOException e) {
                    throw new StatementExecutionException(String.format("Unexpected error in closing %s", value));
                }
                fileTable.remove(fileName.getVal());
                state.setFileTable(fileTable);

            } else throw new StatementExecutionException(fileName + " not in FileTable");
        } else throw new StatementExecutionException(expression.toString() + " couldn't be evaluated as a string");
        return null;
    }

    @Override
    public String toString() {
        return "closeRFile(" + expression.toString() + ")";
    }
}
