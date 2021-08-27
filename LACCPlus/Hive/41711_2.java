//,temp,HS2ActivePassiveHARegistry.java,269,281,temp,HS2ActivePassiveHARegistry.java,253,267
//,3
public class xxx {
    @Override
    public void isLeader() {
      // only leader publishes instance uri as endpoint which will be used by clients to make connections to HS2 via
      // service discovery.
      try {
        if (!hasLeadership()) {
          LOG.info("isLeader notification received but hasLeadership returned false.. awaiting..");
          leaderLatch.await();
        }
        addActiveEndpointToServiceRecord();
        LOG.info("HS2 instance in ACTIVE mode. Service record: {}", srv);
      } catch (Exception e) {
        throw new ServiceException("Unable to add active endpoint to service record", e);
      }
    }

};