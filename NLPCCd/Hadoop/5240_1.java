//,temp,sample_1447.java,2,15,temp,sample_1444.java,2,15
//,3
public class xxx {
public RMStateStoreState transition(RMStateStore store, RMStateStoreEvent event) {
if (!(event instanceof RMStateStoreRMDTEvent)) {
return RMStateStoreState.ACTIVE;
}
boolean isFenced = false;
RMStateStoreRMDTEvent dtEvent = (RMStateStoreRMDTEvent) event;
try {
store.removeRMDelegationTokenState(dtEvent.getRmDTIdentifier());
} catch (Exception e) {


log.info("error while removing rmdelegationtoken and sequencenumber");
}
}

};