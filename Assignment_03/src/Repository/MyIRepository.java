package Repository;
import Domain.ProgramState.PrgState;
import Exceptions.ADTException;

import java.io.IOException;

public interface MyIRepository {
   PrgState getCrtPrg();
    void logPrgStateExec();
}
