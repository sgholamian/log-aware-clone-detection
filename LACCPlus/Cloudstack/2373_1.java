//,temp,ImplicitDedicationPlanner.java,154,174,temp,UserVmManagerImpl.java,5466,5483
//,3
public class xxx {
    private boolean checkHostSuitabilityForImplicitDedication(Long accountId, List<VMInstanceVO> allVmsOnHost) {
        boolean suitable = true;
        if (allVmsOnHost.isEmpty())
            return false;

        for (VMInstanceVO vm : allVmsOnHost) {
            if (vm.getAccountId() != accountId) {
                s_logger.info("Host " + vm.getHostId() + " found to be unsuitable for implicit dedication as it is " + "running instances of another account");
                suitable = false;
                break;
            } else {
                if (!isImplicitPlannerUsedByOffering(vm.getServiceOfferingId())) {
                    s_logger.info("Host " + vm.getHostId() + " found to be unsuitable for implicit dedication as it " +
                        "is running instances of this account which haven't been created using implicit dedication.");
                    suitable = false;
                    break;
                }
            }
        }
        return suitable;
    }

};