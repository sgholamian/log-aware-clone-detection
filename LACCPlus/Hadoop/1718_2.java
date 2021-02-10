//,temp,NMContainerTokenSecretManager.java,214,234,temp,DelegationTokenRenewer.java,792,818
//,3
public class xxx {
    @Override
    public void run() {
      List<ApplicationId> toCancel = new ArrayList<ApplicationId>();
      while (!Thread.currentThread().isInterrupted()) {
        Iterator<Entry<ApplicationId, Long>> it =
            delayedRemovalMap.entrySet().iterator();
        toCancel.clear();
        while (it.hasNext()) {
          Entry<ApplicationId, Long> e = it.next();
          if (e.getValue() < System.currentTimeMillis()) {
            toCancel.add(e.getKey());
          }
        }
        for (ApplicationId appId : toCancel) {
          removeApplicationFromRenewal(appId);
          delayedRemovalMap.remove(appId);
        }
        synchronized (this) {
          try {
            wait(waitTimeMs);
          } catch (InterruptedException e) {
            LOG.info("Delayed Deletion Thread Interrupted. Shutting it down");
            return;
          }
        }
      }
    }

};