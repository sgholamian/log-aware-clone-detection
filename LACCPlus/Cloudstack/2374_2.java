//,temp,ImplicitDedicationPlanner.java,194,211,temp,UserVmManagerImpl.java,5485,5500
//,3
public class xxx {
    private boolean isImplicitPlannerUsedByOffering(long offeringId) {
        boolean implicitPlannerUsed = false;
        ServiceOfferingVO offering = _serviceOfferingDao.findByIdIncludingRemoved(offeringId);
        if (offering == null) {
            s_logger.error("Couldn't retrieve the offering by the given id : " + offeringId);
        } else {
            String plannerName = offering.getDeploymentPlanner();
            if (plannerName != null) {
                if (plannerName.equals("ImplicitDedicationPlanner")) {
                    implicitPlannerUsed = true;
                }
            }
        }

        return implicitPlannerUsed;
    }

};