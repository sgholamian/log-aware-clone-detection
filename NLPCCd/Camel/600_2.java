//,temp,sample_1601.java,2,8,temp,sample_3378.java,2,10
//,3
public class xxx {
protected void doStart() throws Exception {
super.doStart();
DistributedGroup group = getAtomixEndpoint().getAtomix().getGroup( groupName, new DistributedGroup.Config(getAtomixEndpoint().getConfiguration().getResourceOptions(groupName)), new DistributedGroup.Options(getAtomixEndpoint().getConfiguration().getResourceConfig(groupName)) ).join();
this.localMember = group.join(memberName).join();
this.consumer = localMember.messaging().consumer(channelName);


log.info("subscribe to group member channel");
}

};