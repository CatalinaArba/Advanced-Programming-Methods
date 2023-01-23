package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.Expression.Exp;
import Domain.ProgramState.PrgState;
import Domain.Type.StringType;
import Domain.Type.Type;
import Domain.Value.StringValue;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStmt {
    private final Exp expression;

    public OpenRFile(Exp expression) {
        this.expression = expression;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementExecutionException, ExpressionEvaluationException, ADTException {
        if (expression.typeCheck(typeEnv).equals(new StringType()))
            return typeEnv;
        else
            throw new StatementExecutionException("OpenReadFile requires a string expression.");
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionEvaluationException, StatementExecutionException {

        //evaluate the exp and check whether its type is a StringType.
        Value value = expression.eval(state.getSymTable(), state.getHeap());
        if (value.getType().equals(new StringType())) {
            //check whether the string value is not already a key in the FileTable.
            StringValue fileName = (StringValue) value;
            MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
            if (!fileTable.isDefined(fileName.getVal())) {
                //open in Java the file having the name equals to the computed string value, using an instance of the BufferedReader class.
                BufferedReader bufferedReader;
                try {
                    bufferedReader = new BufferedReader(new FileReader(fileName.getVal()));
                } catch (FileNotFoundException e) {
                    throw new StatementExecutionException(fileName.getVal() + " doesn't exist or couldn't be opened!");
                }
                //create a new entrance into the FileTable which maps the above computed string to the instance of the BufferedReader class created before.
                fileTable.put(fileName.getVal(), bufferedReader);
                state.setFileTable(fileTable);
            } else {
                throw new StatementExecutionException(fileName.getVal() + " is already defined");
            }


        } else {
            throw new StatementExecutionException(state + " expression wasn't evaluated as a String");
        }
        return null;
    }

    public String toString() {
        return "openRFile(" + expression.toString() + ")";
    }

}
