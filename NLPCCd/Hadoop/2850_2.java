//,temp,sample_1453.java,2,15,temp,sample_1456.java,2,15
//,2
public class xxx {
public RMStateStoreState transition(RMStateStore store, RMStateStoreEvent event) {
if (!(event instanceof RMStateStoreRMDTMasterKeyEvent)) {
return RMStateStoreState.ACTIVE;
}
boolean isFenced = false;
RMStateStoreRMDTMasterKeyEvent dtEvent = (RMStateStoreRMDTMasterKeyEvent) event;
try {
store.removeRMDTMasterKeyState(dtEvent.getDelegationKey());
} catch (Exception e) {


log.info("error while removing rmdtmasterkey");
}
}

};