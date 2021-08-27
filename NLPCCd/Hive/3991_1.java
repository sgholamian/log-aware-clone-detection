//,temp,sample_4403.java,2,18,temp,sample_4402.java,2,13
//,3
public class xxx {
public void dummy_method(){
OperationHandle opHandle = null;
try {
opHandle = executeStatementInternal(cmd_trimed, null, false, 0);
} catch (HiveSQLException e) {
return -1;
}
if (opHandle != null) {
try {
closeOperation(opHandle);
} catch (HiveSQLException e) {


log.info("failed to close operation for command in hiverc file");
}
}
}

};