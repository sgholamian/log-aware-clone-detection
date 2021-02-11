//,temp,HMaster.java,3900,3906,temp,HMaster.java,3892,3898
//,3
public class xxx {
  public void remoteProcedureCompleted(long procId) {
    LOG.debug("Remote procedure done, pid={}", procId);
    RemoteProcedure<MasterProcedureEnv, ?> procedure = getRemoteProcedure(procId);
    if (procedure != null) {
      procedure.remoteOperationCompleted(procedureExecutor.getEnvironment());
    }
  }

};