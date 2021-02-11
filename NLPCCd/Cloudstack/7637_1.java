//,temp,sample_3545.java,2,17,temp,sample_3544.java,2,17
//,2
public class xxx {
public void dummy_method(){
clusterDasVmConfigSpec.setInfo(clusterDasVmConfigInfo);
ClusterConfigSpecEx clusterConfigSpecEx = new ClusterConfigSpecEx();
ClusterDasConfigInfo clusterDasConfigInfo = new ClusterDasConfigInfo();
clusterConfigSpecEx.setDasConfig(clusterDasConfigInfo);
clusterConfigSpecEx.getDasVmConfigSpec().add(clusterDasVmConfigSpec);
ManagedObjectReference morTask = _context.getService().reconfigureComputeResourceTask(_mor, clusterConfigSpecEx, true);
boolean result = _context.getVimClient().waitForTask(morTask);
if (result) {
_context.waitForTaskProgressDone(morTask);
} else {


log.info("set restart priority failed for vm due to");
}
}

};