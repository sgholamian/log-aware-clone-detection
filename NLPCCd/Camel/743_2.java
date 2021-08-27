//,temp,sample_1609.java,2,15,temp,sample_1610.java,2,16
//,3
public class xxx {
public void reConnectToQueue() {
try {
getEndpoint().createQueue(getClient());
} catch (QueueDeletedRecentlyException qdr) {
try {
Thread.sleep(30000);
getEndpoint().createQueue(getClient());
} catch (Exception e) {
}
} catch (Exception e) {


log.info("could not connect to queue in amazon");
}
}

};