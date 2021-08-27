//,temp,sample_5374.java,2,16,temp,sample_5375.java,2,16
//,3
public class xxx {
public void dummy_method(){
group.election().onElection(term -> {
if (isRunAllowed()) {
fireLeadershipChangedEvent(Optional.of(toClusterMember(term.leader())));
}
});
group.onJoin(member -> {
if (isRunAllowed()) {
fireMemberAddedEvent(toClusterMember(member));
}
});


log.info("listen leave events");
}

};