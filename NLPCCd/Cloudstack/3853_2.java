//,temp,sample_1062.java,2,9,temp,sample_1049.java,2,9
//,2
public class xxx {
private Pair<JobInfo.Status, String> orchestrateStart(final VmWorkStart work) throws Exception {
final VMInstanceVO vm = _entityMgr.findById(VMInstanceVO.class, work.getVmId());
if (vm == null) {


log.info("unable to find vm");
}
}

};