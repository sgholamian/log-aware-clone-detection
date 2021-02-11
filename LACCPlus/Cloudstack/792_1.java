//,temp,AutoScaleManagerImpl.java,1278,1286,temp,AutoScaleManagerImpl.java,1267,1276
//,3
public class xxx {
    private boolean checkConditionDown(AutoScaleVmGroupVO asGroup) {
        Integer currentVM = _autoScaleVmGroupVmMapDao.countByGroup(asGroup.getId());
        Integer minVm = asGroup.getMinMembers();
        if (currentVM - 1 < minVm) {
            s_logger.warn("number of VM will less than the minimum in this group if scaling down, so do nothing more");
            return false;
        }
        return true;
    }

};