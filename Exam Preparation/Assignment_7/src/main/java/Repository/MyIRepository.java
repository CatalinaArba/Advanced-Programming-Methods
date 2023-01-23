package Repository;
import Domain.ProgramState.PrgState;
import Exceptions.ADTException;

import java.io.IOException;
import java.util.List;

public interface MyIRepository {

    void logPrgStateExec(PrgState prgState) throws IOException, ADTException;;

    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prgStates);
}
