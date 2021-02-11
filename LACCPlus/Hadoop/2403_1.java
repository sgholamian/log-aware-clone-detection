//,temp,RetryInvocationHandler.java,391,415,temp,SchedulerMetrics.java,660,673
//,3
public class xxx {
  private void log(final Method method, final boolean isFailover,
      final int failovers, final long delay, final Exception ex) {
    // log info if this has made some successful calls or
    // this is not the first failover
    final boolean info = hasSuccessfulCall || failovers != 0
        || asyncCallHandler.hasSuccessfulCall();
    if (!info && !LOG.isDebugEnabled()) {
      return;
    }

    final StringBuilder b = new StringBuilder()
        .append(ex + ", while invoking ")
        .append(proxyDescriptor.getProxyInfo().getString(method.getName()));
    if (failovers > 0) {
      b.append(" after ").append(failovers).append(" failover attempts");
    }
    b.append(isFailover? ". Trying to failover ": ". Retrying ");
    b.append(delay > 0? "after sleeping for " + delay + "ms.": "immediately.");

    if (info) {
      LOG.info(b.toString());
    } else {
      LOG.debug(b.toString(), ex);
    }
  }

};