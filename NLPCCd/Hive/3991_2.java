//,temp,sample_4403.java,2,18,temp,sample_4402.java,2,13
//,3
public class xxx {
protected int processCmd(String cmd) {
int rc = 0;
String cmd_trimed = cmd.trim();
OperationHandle opHandle = null;
try {
opHandle = executeStatementInternal(cmd_trimed, null, false, 0);
} catch (HiveSQLException e) {


log.info("failed to execute command in global hiverc file");
}
}

};