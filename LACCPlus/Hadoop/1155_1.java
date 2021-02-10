//,temp,FiCaSchedulerUtils.java,25,46,temp,SchedulerAppUtils.java,25,46
//,2
public class xxx {
  public static  boolean isBlacklisted(FiCaSchedulerApp application,
      FiCaSchedulerNode node, Log LOG) {
    if (application.isBlacklisted(node.getNodeName())) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Skipping 'host' " + node.getNodeName() + 
            " for " + application.getApplicationId() + 
            " since it has been blacklisted");
      }
      return true;
    }

    if (application.isBlacklisted(node.getRackName())) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Skipping 'rack' " + node.getRackName() + 
            " for " + application.getApplicationId() + 
            " since it has been blacklisted");
      }
      return true;
    }

    return false;
  }

};