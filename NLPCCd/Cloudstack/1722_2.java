//,temp,sample_4057.java,2,9,temp,sample_4053.java,2,9
//,3
public class xxx {
public void doScaleUp(long groupId, Integer numVm) {
AutoScaleVmGroupVO asGroup = _autoScaleVmGroupDao.findById(groupId);
if (asGroup == null) {


log.info("can not find the groupid for scaling up");
}
}

};