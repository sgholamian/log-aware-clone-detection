//,temp,TestHs2Hooks.java,122,134,temp,TestHs2Hooks.java,107,120
//,3
public class xxx {
    @Override
    public void postAnalyze(HiveSemanticAnalyzerHookContext context,
        List<Task<?>> rootTasks) throws SemanticException {
      try {
        userName = context.getUserName();
        ipAddress = context.getIpAddress();
        command = context.getCommand();
        commandType = context.getHiveOperation();
      } catch (Throwable t) {
        LOG.error("Error in semantic analysis hook postAnalyze: " + t, t);
        postAnalyzeError = t;
      }
    }

};