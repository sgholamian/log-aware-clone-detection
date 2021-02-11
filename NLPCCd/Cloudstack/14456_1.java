//,temp,sample_5787.java,2,10,temp,sample_5789.java,2,10
//,2
public class xxx {
private void onClusterNodeJoined(Object sender, ClusterNodeJoinEventArgs args) {
if (s_logger.isDebugEnabled()) {
for (ManagementServerHostVO mshost : args.getJoinedNodes()) {


log.info("handle cluster node join alert joined node msidl");
}
}
}

};