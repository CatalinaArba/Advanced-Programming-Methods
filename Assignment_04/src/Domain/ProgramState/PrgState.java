package Domain.ProgramState;
import Domain.ADT.MyIStack;
import Domain.Type.*;
import Domain.Statement.*;
import Domain.Value.*;
import Domain.ADT.*;
import  java.io.BufferedReader;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class PrgState {
    private MyIStack<IStmt>  exeStack;
    private MyIDictionary<String, Value>   symTable;
    private MyIList<Value>   out;
    private IStmt   originalProgram; //optional field, but good to have
    private MyIDictionary<String, BufferedReader> fileTable;
    private MyIHeap   heap;


    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String,Value> symtbl, MyIList<Value> ot, MyIDictionary<String, BufferedReader> fileTable,MyIHeap heap, IStmt program){
        exeStack=stk;
        symTable=symtbl;
        out = ot;
        this.fileTable=fileTable;
        originalProgram=program;
        this.heap=heap;
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

    public MyIDictionary<String,Value> getSymTable(){return symTable;}

    public MyIHeap getHeap(){return heap;}
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

    public void setHeap(MyIHeap newHeap){ heap=newHeap;}
    public void  setFileTable(MyIDictionary<String, BufferedReader> fileTable){
        this.fileTable=fileTable;
    }


    @Override
    public String toString() {
        //return "Execution stack: \n" + exeStack.toString() + "\nSymbol table: \n" + symTable.toString() + "\nOutput list: \n" + out.toString() + "\n";
        return  "EXE STACK" + "\n" + exeStack.toString() + "\n" +
                "SYMBOL TABLE" + "\n" + symTable.toString() + "\n" +
                "OUT" + "\n" + out.toString() + "\n" +
                "FILE TABLE" + "\n" + fileTable.toString() + "\n" +
                "HEAP "+"\n"+heap.toString()+"\n";

    }

}
