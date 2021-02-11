//,temp,sample_7568.java,2,7,temp,sample_2588.java,2,8
//,3
public class xxx {
protected void stopContainerInternal(ContainerId containerID) throws YarnException, IOException {
String containerIDStr = containerID.toString();
Container container = this.context.getContainers().get(containerID);


log.info("stopping container with container id");
}

};