//,temp,sample_5374.java,2,16,temp,sample_5375.java,2,16
//,3
public class xxx {
public void dummy_method(){
group.onJoin(member -> {
if (isRunAllowed()) {
fireMemberAddedEvent(toClusterMember(member));
}
});
group.onLeave(member -> {
if (isRunAllowed()) {
fireMemberRemovedEvent(toClusterMember(member));
}
});


log.info("join group");
}

};