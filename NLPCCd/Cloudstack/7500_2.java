//,temp,sample_1062.java,2,9,temp,sample_1057.java,2,9
//,2
public class xxx {
private Pair<JobInfo.Status, String> orchestrateReboot(final VmWorkReboot work) throws Exception {
final VMInstanceVO vm = _entityMgr.findById(VMInstanceVO.class, work.getVmId());
if (vm == null) {


log.info("unable to find vm");
}
}

};