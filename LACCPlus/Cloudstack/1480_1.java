//,temp,AlertManagerImpl.java,418,427,temp,DeploymentPlanningManagerImpl.java,876,885
//,2
public class xxx {
        @Override
        protected void runInContext() {
            try {
                s_logger.debug("Running Capacity Checker ... ");
                checkForAlerts();
                s_logger.debug("Done running Capacity Checker ... ");
            } catch (Throwable t) {
                s_logger.error("Exception in CapacityChecker", t);
            }
        }

};