//,temp,sample_4045.java,2,10,temp,sample_4046.java,2,10
//,3
public class xxx {
private boolean checkConditionDown(AutoScaleVmGroupVO asGroup) {
Integer currentVM = _autoScaleVmGroupVmMapDao.countByGroup(asGroup.getId());
Integer minVm = asGroup.getMinMembers();
if (currentVM - 1 < minVm) {


log.info("number of vm will less than the minimum in this group if scaling down so do nothing more");
}
}

};