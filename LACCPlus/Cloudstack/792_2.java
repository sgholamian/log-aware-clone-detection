//,temp,AutoScaleManagerImpl.java,1278,1286,temp,AutoScaleManagerImpl.java,1267,1276
//,3
public class xxx {
    private boolean checkConditionUp(AutoScaleVmGroupVO asGroup, Integer numVm) {
        // check maximum
        Integer currentVM = _autoScaleVmGroupVmMapDao.countByGroup(asGroup.getId());
        Integer maxVm = asGroup.getMaxMembers();
        if (currentVM + numVm > maxVm) {
            s_logger.warn("number of VM will greater than the maximum in this group if scaling up, so do nothing more");
            return false;
        }
        return true;
    }

};