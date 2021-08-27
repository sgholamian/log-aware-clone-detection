//,temp,sample_2404.java,2,11,temp,sample_2403.java,2,10
//,2
public class xxx {
public void cancelOperation(OperationHandle opHandle, String errMsg) throws HiveSQLException {
Operation operation = getOperation(opHandle);
OperationState opState = operation.getStatus().getState();
if (opState.isTerminal()) {
} else {


log.info("attempting to cancel from state");
}
}

};