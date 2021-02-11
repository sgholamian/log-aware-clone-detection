//,temp,ProcedureExecutor.java,2071,2088,temp,RootProcedureState.java,145,155
//,3
public class xxx {
  protected synchronized void addRollbackStep(Procedure<TEnvironment> proc) {
    if (proc.isFailed()) {
      state = State.FAILED;
    }
    if (subprocStack == null) {
      subprocStack = new ArrayList<>();
    }
    proc.addStackIndex(subprocStack.size());
    LOG.debug("Add procedure {} as the {}th rollback step", proc, subprocStack.size());
    subprocStack.add(proc);
  }

};