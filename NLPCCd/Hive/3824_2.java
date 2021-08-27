//,temp,sample_2523.java,2,7,temp,sample_2520.java,2,7
//,3
public class xxx {
public void cancelOperation(OperationHandle opHandle) throws HiveSQLException {
sessionManager.getOperationManager().getOperation(opHandle) .getParentSession().cancelOperation(opHandle);


log.info("canceloperation");
}

};