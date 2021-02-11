//,temp,AlertManagerImpl.java,418,427,temp,DeploymentPlanningManagerImpl.java,876,885
//,2
public class xxx {
        @Override
        protected void runInContext() {
            try {
                s_logger.debug("Checking if any host reservation can be released ... ");
                checkHostReservations();
                s_logger.debug("Done running HostReservationReleaseChecker ... ");
            } catch (Throwable t) {
                s_logger.error("Exception in HostReservationReleaseChecker", t);
            }
        }

};