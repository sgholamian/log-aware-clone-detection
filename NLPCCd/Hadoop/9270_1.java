//,temp,sample_3665.java,2,10,temp,sample_2634.java,2,12
//,3
public class xxx {
public void verifyRMHeartbeatResponseForNodeLabels( NodeHeartbeatResponse response) {
if (areLabelsSentToRM) {
if (response.getAreNodeLabelsAcceptedByRM() && LOG.isDebugEnabled()) {


log.info("node labels were accepted by rm");
}
}
}

};