//,temp,sample_250.java,2,8,temp,sample_4714.java,2,8
//,2
public class xxx {
protected void doStart() throws Exception {
super.doStart();
this.value = getAtomixEndpoint() .getAtomix() .getValue( resourceName, new DistributedValue.Config(getAtomixEndpoint().getConfiguration().getResourceOptions(resourceName)), new DistributedValue.Options(getAtomixEndpoint().getConfiguration().getResourceConfig(resourceName))) .join();


log.info("subscribe to events for queue");
}

};