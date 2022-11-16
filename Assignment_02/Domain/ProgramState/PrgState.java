package Domain.ProgramState;
import Domain.ADT.MyIStack;
import Domain.Type.*;
import Domain.Statement.*;
import Domain.Value.*;
import Domain.ADT.*;

import java.util.List;

public class PrgState {
    MyIStack<IStmt>  exeStack;
    MyIDictionary<String, Value>   symTable;

    MyIList<Value>   out;
    IStmt   originalProgram;
    //optional field, but good to have
    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String,Value> symtbl, MyIList<Value> ot,IStmt prg){
        exeStack=stk;
        symTable=symtbl;
        out = ot;
        //originalProgram=deepCopy(prg);
        //recreate the entire original prg
         stk.push(prg);
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

    public void setSymTable(MyIDictionary<String, Value> newSymTable){
        symTable=newSymTable;
    }

    public void setExeStack(MyIStack<IStmt> newStack){
        exeStack=newStack;
    }

    public void setOut(MyIList<Value> newList){
        out=newList;
    }
    @Override
    public String toString() {
        return "Execution stack: \n" + exeStack.toString() + "\nSymbol table: \n" + symTable.toString() + "\nOutput list: \n" + out.toString() + "\n";
    }
}
