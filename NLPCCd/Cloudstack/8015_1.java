//,temp,sample_4045.java,2,10,temp,sample_4046.java,2,10
//,3
public class xxx {
private boolean checkConditionUp(AutoScaleVmGroupVO asGroup, Integer numVm) {
Integer currentVM = _autoScaleVmGroupVmMapDao.countByGroup(asGroup.getId());
Integer maxVm = asGroup.getMaxMembers();
if (currentVM + numVm > maxVm) {


log.info("number of vm will greater than the maximum in this group if scaling up so do nothing more");
}
}

};