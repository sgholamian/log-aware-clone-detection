//,temp,sample_4760.java,2,13,temp,sample_4759.java,2,13
//,3
public class xxx {
public void postAnalyze(HiveSemanticAnalyzerHookContext context, List<Task<? extends Serializable>> rootTasks) throws SemanticException {
try {
userName = context.getUserName();
ipAddress = context.getIpAddress();
command = context.getCommand();
commandType = context.getHiveOperation();
} catch (Throwable t) {


log.info("error in semantic analysis hook postanalyze");
}
}

};