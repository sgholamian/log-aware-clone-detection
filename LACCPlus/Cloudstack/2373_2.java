//,temp,ImplicitDedicationPlanner.java,154,174,temp,UserVmManagerImpl.java,5466,5483
//,3
public class xxx {
    private boolean checkIfAllVmsCreatedInStrictMode(Long accountId, List<VMInstanceVO> allVmsOnHost) {
        boolean createdByImplicitStrict = true;
        if (allVmsOnHost.isEmpty()) {
            return false;
        }
        for (VMInstanceVO vm : allVmsOnHost) {
            if (!isImplicitPlannerUsedByOffering(vm.getServiceOfferingId()) || vm.getAccountId() != accountId) {
                s_logger.info("Host " + vm.getHostId() + " found to be running a vm created by a planner other" + " than implicit, or running vms of other account");
                createdByImplicitStrict = false;
                break;
            } else if (isServiceOfferingUsingPlannerInPreferredMode(vm.getServiceOfferingId()) || vm.getAccountId() != accountId) {
                s_logger.info("Host " + vm.getHostId() + " found to be running a vm created by an implicit planner" + " in preferred mode, or running vms of other account");
                createdByImplicitStrict = false;
                break;
            }
        }
        return createdByImplicitStrict;
    }

};