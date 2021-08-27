//,temp,ReExecutionDagSubmitPlugin.java,41,52,temp,ReExecuteLostAMQueryPlugin.java,42,58
//,3
public class xxx {
    @Override
    public void run(HookContext hookContext) throws Exception {
      if (hookContext.getHookType() == HookContext.HookType.ON_FAILURE_HOOK) {
        Throwable exception = hookContext.getException();

        if (exception != null && exception.getMessage() != null) {
          // When HS2 does not manage the AMs, tez AMs are registered with zookeeper and HS2 discovers it,
          // failure of unmanaged AMs will throw AM record not being found in zookeeper.
          String unmanagedAMFailure = "AM record not found (likely died)";
          if (lostAMContainerErrorPattern.matcher(exception.getMessage()).matches()
                  || exception.getMessage().contains(unmanagedAMFailure)) {
            retryPossible = true;
          }
          LOG.info("Got exception message: {} retryPossible: {}", exception.getMessage(), retryPossible);
        }
      }
    }

};