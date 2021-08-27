//,temp,TestHs2Hooks.java,122,134,temp,TestHs2Hooks.java,107,120
//,3
public class xxx {
    @Override
    public ASTNode preAnalyze(HiveSemanticAnalyzerHookContext context,
        ASTNode ast) throws SemanticException {
      try {
        userName = context.getUserName();
        ipAddress = context.getIpAddress();
        command = context.getCommand();
        commandType = context.getHiveOperation();
      } catch (Throwable t) {
        LOG.error("Error in semantic analysis hook preAnalyze: " + t, t);
        preAnalyzeError = t;
      }
      return ast;
    }

};