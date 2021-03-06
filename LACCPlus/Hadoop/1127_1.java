//,temp,RMStateStore.java,349,367,temp,RMStateStore.java,306,323
//,2
public class xxx {
    @Override
    public void transition(RMStateStore store, RMStateStoreEvent event) {
      if (!(event instanceof RMStateStoreRMDTEvent)) {
        // should never happen
        LOG.error("Illegal event type: " + event.getClass());
        return;
      }

      RMStateStoreRMDTEvent dtEvent = (RMStateStoreRMDTEvent) event;
      try {
        LOG.info("Updating RMDelegationToken and SequenceNumber");
        store.updateRMDelegationTokenState(
            dtEvent.getRmDTIdentifier(), dtEvent.getRenewDate());
      } catch (Exception e) {
        LOG.error("Error While Updating RMDelegationToken and SequenceNumber ",
            e);
        store.notifyStoreOperationFailed(e);
      }
    }

};