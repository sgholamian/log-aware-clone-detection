//,temp,sample_1601.java,2,8,temp,sample_3378.java,2,10
//,3
public class xxx {
protected void doStart() throws Exception {
super.doStart();
this.queue = getAtomixEndpoint() .getAtomix() .getQueue( resourceName, new DistributedQueue.Config(getAtomixEndpoint().getConfiguration().getResourceOptions(resourceName)), new DistributedQueue.Options(getAtomixEndpoint().getConfiguration().getResourceConfig(resourceName))) .join();


log.info("subscribe to events for queue");
}

};