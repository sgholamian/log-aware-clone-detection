//,temp,sample_3666.java,2,11,temp,sample_6931.java,2,9
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