//,temp,TestRMAppTransitions.java,120,132,temp,ResourceManager.java,779,791
//,3
public class xxx {
    @Override
    public void handle(RMAppAttemptEvent event) {
      ApplicationId appId = event.getApplicationAttemptId().getApplicationId();
      RMApp rmApp = this.rmContext.getRMApps().get(appId);
      if (rmApp != null) {
        try {
          rmApp.getRMAppAttempt(event.getApplicationAttemptId()).handle(event);
        } catch (Throwable t) {
          LOG.error("Error in handling event type " + event.getType()
              + " for application " + appId, t);
        }    
      }
    }

};