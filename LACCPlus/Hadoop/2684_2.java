//,temp,CompositeService.java,72,79,temp,FSQueue.java,289,296
//,3
public class xxx {
  @Override
  public void setFairShare(Resource fairShare) {
    this.fairShare = fairShare;
    metrics.setFairShare(fairShare);
    if (LOG.isDebugEnabled()) {
      LOG.debug("The updated fairShare for " + getName() + " is " + fairShare);
    }
  }

};