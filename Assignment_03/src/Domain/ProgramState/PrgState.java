package Domain.ProgramState;
import Domain.ADT.MyIStack;
import Domain.Type.*;
import Domain.Statement.*;
import Domain.Value.*;
import Domain.ADT.*;
import  java.io.BufferedReader;
import java.util.List;


public class PrgState {
    MyIStack<IStmt>  exeStack;
    MyIDictionary<String, Value>   symTable;
    MyIList<Value>   out;
    IStmt   originalProgram; //optional field, but good to have
    private MyIDictionary<String, BufferedReader> fileTable;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String,Value> symtbl, MyIList<Value> ot, MyIDictionary<String, BufferedReader> fileTable, IStmt program){
        exeStack=stk;
        symTable=symtbl;
        out = ot;
        this.fileTable=fileTable;
        originalProgram=program;
        //originalProgram=deepCopy(prg);
        //recreate the entire original prg
         stk.push(originalProgram);
    }

    public MyIStack<IStmt> getStk()
    {
        return this.exeStack;
    }
    public MyIList<Value> getOut(){
        return out;
    }
    public MyIDictionary<String,Value> getSymTable(){

        return symTable;
    }
    public MyIDictionary<String, BufferedReader> getFileTable(){
        return fileTable;
    }
    public void setSymTable(MyIDictionary<String, Value> newSymTable){
        symTable=newSymTable;
    }

    public void setExeStack(MyIStack<IStmt> newStack){
        exeStack=newStack;
    }

    public void setOut(MyIList<Value> newList){
        out=newList;
    }

    public void  setFileTable(MyIDictionary<String, BufferedReader> fileTable){
        this.fileTable=fileTable;
    }
    @Override
    public String toString() {
        //return "Execution stack: \n" + exeStack.toString() + "\nSymbol table: \n" + symTable.toString() + "\nOutput list: \n" + out.toString() + "\n";
        return  "EXE STACK" + "\n" + exeStack.toString() + "\n" +
                "SYMBOL TABLE" + "\n" + symTable.toString() + "\n" +
                "OUT" + "\n" + out.toString() + "\n" +
                "FILE TABLE" + "\n" + fileTable.toString() + "\n" ;

    }


}
