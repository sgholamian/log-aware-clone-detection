//,temp,sample_250.java,2,8,temp,sample_4714.java,2,8
//,2
public class xxx {
protected void doStart() throws Exception {
super.doStart();
this.set = getAtomixEndpoint() .getAtomix() .getSet( resourceName, new DistributedSet.Config(getAtomixEndpoint().getConfiguration().getResourceOptions(resourceName)), new DistributedSet.Options(getAtomixEndpoint().getConfiguration().getResourceConfig(resourceName))) .join();


log.info("subscribe to events for set");
}

};