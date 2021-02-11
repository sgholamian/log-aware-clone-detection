//,temp,sample_5787.java,2,10,temp,sample_5789.java,2,10
//,2
public class xxx {
private void onClusterNodeLeft(Object sender, ClusterNodeLeftEventArgs args) {
if (s_logger.isDebugEnabled()) {
for (ManagementServerHostVO mshost : args.getLeftNodes()) {


log.info("handle cluster node left alert leaving node msid");
}
}
}

};