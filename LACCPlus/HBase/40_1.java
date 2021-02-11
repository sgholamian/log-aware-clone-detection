//,temp,HMaster.java,3900,3906,temp,HMaster.java,3892,3898
//,3
public class xxx {
  public void remoteProcedureFailed(long procId, RemoteProcedureException error) {
    LOG.debug("Remote procedure failed, pid={}", procId, error);
    RemoteProcedure<MasterProcedureEnv, ?> procedure = getRemoteProcedure(procId);
    if (procedure != null) {
      procedure.remoteOperationFailed(procedureExecutor.getEnvironment(), error);
    }
  }

};