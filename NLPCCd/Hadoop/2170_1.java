//,temp,sample_6827.java,2,14,temp,sample_6828.java,2,15
//,3
public class xxx {
private void doGracefulFailover() throws ServiceFailedException, IOException, InterruptedException {
int timeout = FailoverController.getGracefulFenceTimeout(conf) * 2;
checkEligibleForFailover();
HAServiceTarget oldActive = getCurrentActive();
if (oldActive == null) {
throw new ServiceFailedException( "No other node is currently active.");
}
if (oldActive.getAddress().equals(localTarget.getAddress())) {


log.info("local node is already active no need to failover returning success");
}
}

};