package Domain.Statement;
import Domain.ProgramState.PrgState;
import Domain.ADT.MyIStack;
public class CompStmt implements IStmt{
    IStmt first;
    IStmt snd;
    //......
    public CompStmt(IStmt first, IStmt second){
        this.first=first;
        this.snd=second;
    }


    @Override
    public String toString() {

        return "(" + first.toString() + ";" + snd.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) {
        MyIStack<IStmt> stk = state.getStk();
        stk.push(snd);
        stk.push(first);
        state.setExeStack(stk);
        return state;
    }
}

