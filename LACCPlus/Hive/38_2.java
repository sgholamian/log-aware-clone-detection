//,temp,CLIService.java,544,551,temp,CLIService.java,533,539
//,3
public class xxx {
  @Override
  public void closeOperation(OperationHandle opHandle)
      throws HiveSQLException {
    sessionManager.getOperationManager().getOperation(opHandle)
    .getParentSession().closeOperation(opHandle);
    LOG.debug(opHandle + ": closeOperation");
  }

};