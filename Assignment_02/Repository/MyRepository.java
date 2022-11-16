package Repository;

import java.util.ArrayList;
import java.util.List;
import Domain.ProgramState.PrgState;

public class MyRepository implements MyIRepository{
    private List<PrgState> programStates;
    private int currentPosition;

    public MyRepository(PrgState prgState){
        programStates=new ArrayList<>();
        currentPosition=0;
        programStates.add(prgState);
    }

    @Override
    public PrgState getCrtPrg(){

        return programStates.get(currentPosition);
    }
}
