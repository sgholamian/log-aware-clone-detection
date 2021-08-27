//,temp,ReExecutionDagSubmitPlugin.java,41,52,temp,ReExecuteLostAMQueryPlugin.java,42,58
//,3
public class xxx {
    @Override
    public void run(HookContext hookContext) throws Exception {
      if (hookContext.getHookType() == HookType.ON_FAILURE_HOOK) {
        Throwable exception = hookContext.getException();
        if (exception != null && exception.getMessage() != null) {
          if (exception.getMessage().contains("Dag submit failed")) {
            retryPossible = true;
          }
          LOG.info("Got exception message: {} retryPossible: {}", exception.getMessage(), retryPossible);
        }
      }
    }

};