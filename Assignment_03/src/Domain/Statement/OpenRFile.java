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
import Domain.ADT.MyDictionary;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Statement;

public class OpenRFile implements IStmt{
    private Exp expression;

    //constructor
    public OpenRFile(Exp expression){
        this.expression=expression;
    }

    //execute function

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionEvaluationException, StatementExecutionException {

        //evaluate the exp and check whether its type is a StringType.
        Value value=expression.eval(state.getSymTable());
        if(value.getType().equals(new StringType())){
            //check whether the string value is not already a key in the FileTable.
            StringValue fileName=(StringValue) value;
            MyIDictionary<String, BufferedReader> fileTable=state.getFileTable();
            if (!fileTable.isDefined(fileName.getVal())){
                //open in Java the file having the name equals to the computed string value, using an instance of the BufferedReader class.
                BufferedReader bufferedReader;
                try{
                    bufferedReader=new BufferedReader(new FileReader(fileName.getVal()));
                }
                catch (FileNotFoundException e)
                {
                    throw new StatementExecutionException(fileName.getVal().toString()+" doesn't exist or couldn't be opened!");
                }
                //create a new entrance into the FileTable which maps the above computed string to the instance of the BufferedReader class created before.
                fileTable.put(fileName.getVal(),bufferedReader);
                state.setFileTable(fileTable);
            }
            else{
                throw new StatementExecutionException(fileName.getVal().toString()+" is already defined");
            }


        }
        else{
            throw new StatementExecutionException(state.toString()+" expression wasn't evaluated as a String");
        }
        return state;
    }

    public String toString() {
        return "openRFile(" + expression.toString() + ")";
    }

}
