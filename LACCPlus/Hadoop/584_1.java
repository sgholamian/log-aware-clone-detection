//,temp,ZKFailoverController.java,159,186,temp,ZKFailoverController_before_log_fix.java,159,185
//,2
public class xxx {
  public int run(final String[] args) throws Exception {
    if (!localTarget.isAutoFailoverEnabled()) {
      LOG.fatal("Automatic failover is not enabled for " + localTarget + "." +
          " Please ensure that automatic failover is enabled in the " +
          "configuration before running the ZK failover controller.");
      return ERR_CODE_AUTO_FAILOVER_NOT_ENABLED;
    }
    loginAsFCUser();
    try {
      return SecurityUtil.doAsLoginUserOrFatal(new PrivilegedAction<Integer>() {
        @Override
        public Integer run() {
          try {
            return doRun(args);
          } catch (Exception t) {
            throw new RuntimeException(t);
          } finally {
            if (elector != null) {
              elector.terminateConnection();
            }
          }
        }
      });
    } catch (RuntimeException rte) {
      LOG.fatal("The failover controller encounters runtime error: " + rte);
      throw (Exception)rte.getCause();
    }
  }

};