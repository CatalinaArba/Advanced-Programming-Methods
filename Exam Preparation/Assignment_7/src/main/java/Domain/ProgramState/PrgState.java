package Domain.ProgramState;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.ADT.MyIList;
import Domain.ADT.MyIStack;
import Domain.Statement.IStmt;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;
import Domain.Value.Value;

import java.io.BufferedReader;
import java.util.List;


public class PrgState {
    private MyIStack<IStmt>  exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private IStmt   originalProgram; //optional field, but good to have
    private MyIDictionary<String, BufferedReader> fileTable;
    private MyIHeap heap;

    private int id;
    private static int lastId = 0;

    public Boolean isNotCompleted(){
        return exeStack.isEmpty();
    }

    public synchronized int setId() {
        lastId++;
        return lastId;
    }
    public int getId() {
        return this.id;
    }

    //A5
    public PrgState oneStep() throws StatementExecutionException, ADTException, ExpressionEvaluationException {
        if(exeStack.isEmpty())
            throw  new StatementExecutionException("prgstate stack is empty");
        IStmt  crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }


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
        this.id=setId();
    }


    public PrgState(MyIStack<IStmt> stack, MyIDictionary<String,Value> symTable, MyIList<Value> out, MyIDictionary<String, BufferedReader> fileTable, MyIHeap heap) {
        this.exeStack = stack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = setId();
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
        return  "ID: " + id+"\n"+
                "EXE STACK" + "\n" + exeStack.toString() + "\n" +
                "SYMBOL TABLE" + "\n" + symTable.toString() + "\n" +
                "OUT" + "\n" + out.toString() + "\n" +
                "FILE TABLE" + "\n" + fileTable.toString() + "\n" +
                "HEAP "+"\n"+heap.toString()+"\n";

    }


    public String exeStackToString() {
        StringBuilder exeStackStringBuilder = new StringBuilder();
        List<IStmt> stack = exeStack.getReversed();
        for (IStmt statement: stack) {
            exeStackStringBuilder.append(statement.toString()).append("\n");
        }
        return exeStackStringBuilder.toString();
    }

    public String symTableToString() throws ADTException {
        StringBuilder symTableStringBuilder = new StringBuilder();
        for (String key: symTable.keySet()) {
            symTableStringBuilder.append(String.format("%s -> %s\n", key, symTable.lookUp(key).toString()));
        }
        return symTableStringBuilder.toString();
    }

    public String outToString() {
        StringBuilder outStringBuilder = new StringBuilder();
        for (Value elem: out.getList()) {
            outStringBuilder.append(String.format("%s\n", elem.toString()));
        }
        return outStringBuilder.toString();
    }

    public String fileTableToString() {
        StringBuilder fileTableStringBuilder = new StringBuilder();
        for (String key: fileTable.keySet()) {
            fileTableStringBuilder.append(String.format("%s\n", key));
        }
        return fileTableStringBuilder.toString();
    }

    public String heapToString() throws ADTException {
        StringBuilder heapStringBuilder = new StringBuilder();
        for (int key: heap.keySet()) {
            heapStringBuilder.append(String.format("%d -> %s\n", key, heap.get(key)));
        }
        return heapStringBuilder.toString();
    }

    public String programStateToString() throws ADTException {
        return "Id: " + id + "\nExecution stack: \n" + exeStackToString() + "Symbol table: \n" + symTableToString() + "Output list: \n" + outToString() + "File table:\n" + fileTableToString() + "Heap memory:\n" + heapToString();
    }
}
