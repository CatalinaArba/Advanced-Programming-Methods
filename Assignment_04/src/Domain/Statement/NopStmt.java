package Domain.Statement;

import Domain.ProgramState.PrgState;

class NopStmt implements IStmt {
    @Override
    public PrgState execute(PrgState state) {
        return null;
    }

    @Override
    public String toString() {
        return "NopStatement";
    }
}
