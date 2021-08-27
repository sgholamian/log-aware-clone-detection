//,temp,HS2ActivePassiveHARegistry.java,269,281,temp,HS2ActivePassiveHARegistry.java,253,267
//,3
public class xxx {
    @Override
    public void notLeader() {
      try {
        if (hasLeadership()) {
          LOG.info("notLeader notification received but hasLeadership returned true.. awaiting..");
          leaderLatch.await();
        }
        addPassiveEndpointToServiceRecord();
        LOG.info("HS2 instance lost leadership. Switched to PASSIVE standby mode. Service record: {}", srv);
      } catch (Exception e) {
        throw new ServiceException("Unable to add passive endpoint to service record", e);
      }
    }

};