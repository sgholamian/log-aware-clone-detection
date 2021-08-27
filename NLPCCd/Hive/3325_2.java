//,temp,sample_4757.java,2,14,temp,sample_4758.java,2,14
//,2
public class xxx {
public void run(HookContext hookContext) {
try {
if (hookContext.getHookType().equals(HookType.PRE_EXEC_HOOK)) {
ipAddress = hookContext.getIpAddress();
userName = hookContext.getUserName();
operation = hookContext.getOperationName();
}
} catch (Throwable t) {


log.info("error in preexechook");
}
}

};