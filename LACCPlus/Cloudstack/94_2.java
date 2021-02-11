//,temp,HAManagerImpl.java,545,554,temp,HAManagerImpl.java,531,543
//,3
public class xxx {
    @Override
    public boolean preStateTransitionEvent(final HAConfig.HAState oldState, final HAConfig.Event event, final HAConfig.HAState newState, final HAConfig haConfig, final boolean status, final Object opaque) {
        if (oldState != newState || newState == HAConfig.HAState.Suspect || newState == HAConfig.HAState.Checking) {
            return false;
        }
        if (LOG.isTraceEnabled()) {
            LOG.trace("HA state pre-transition:: new state=" + newState + ", old state=" + oldState + ", for resource id=" + haConfig.getResourceId() + ", status=" + status + ", ha config state=" + haConfig.getState());
        }
        if (status && haConfig.getState() != newState) {
            LOG.warn("HA state pre-transition:: HA state is not equal to transition state, HA state=" + haConfig.getState() + ", new state=" + newState);
        }
        return processHAStateChange(haConfig, newState, status);
    }

};