//,temp,sample_1051.java,2,9,temp,sample_1056.java,2,9
//,2
public class xxx {
private Pair<JobInfo.Status, String> orchestrateStop(final VmWorkStop work) throws Exception {
final VMInstanceVO vm = _entityMgr.findById(VMInstanceVO.class, work.getVmId());
if (vm == null) {


log.info("unable to find vm");
}
}

};