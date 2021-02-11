//,temp,sample_7166.java,2,9,temp,sample_3666.java,2,11
//,3
public class xxx {
public void verifyRMHeartbeatResponseForNodeLabels( NodeHeartbeatResponse response) {
if (areLabelsSentToRM) {
if (response.getAreNodeLabelsAcceptedByRM() && LOG.isDebugEnabled()) {
} else {


log.info("nm node labels were not accepted by rm and message from rm");
}
}
}

};