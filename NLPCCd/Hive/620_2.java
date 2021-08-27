//,temp,sample_2522.java,2,7,temp,sample_2521.java,2,7
//,3
public class xxx {
public void closeOperation(OperationHandle opHandle) throws HiveSQLException {
sessionManager.getOperationManager().getOperation(opHandle) .getParentSession().closeOperation(opHandle);


log.info("closeoperation");
}

};