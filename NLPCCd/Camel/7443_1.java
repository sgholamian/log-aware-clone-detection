//,temp,sample_5373.java,2,14,temp,sample_5372.java,2,9
//,3
public class xxx {
protected void doStart() throws Exception {
if (!localMember.hasJoined()) {
group = this.atomix.getGroup( getNamespace(), new DistributedGroup.Config(configuration.getResourceConfig(getNamespace())), new DistributedGroup.Options(configuration.getResourceOptions(getNamespace())) ).get();
group.election().onElection(term -> {
if (isRunAllowed()) {
fireLeadershipChangedEvent(Optional.of(toClusterMember(term.leader())));
}
});


log.info("listen join events");
}
}

};