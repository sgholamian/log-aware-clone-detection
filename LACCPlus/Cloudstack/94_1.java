//,temp,HAManagerImpl.java,545,554,temp,HAManagerImpl.java,531,543
//,3
public class xxx {
    @Override
    public boolean postStateTransitionEvent(final StateMachine2.Transition<HAConfig.HAState, HAConfig.Event> transition, final HAConfig haConfig, final boolean status, final Object opaque) {
        if (LOG.isTraceEnabled()) {
            LOG.trace("HA state post-transition:: new state=" + transition.getToState() + ", old state=" + transition.getCurrentState() + ", for resource id=" + haConfig.getResourceId() + ", status=" + status + ", ha config state=" + haConfig.getState());
        }
        if (status && haConfig.getState() != transition.getToState()) {
            LOG.warn("HA state post-transition:: HA state is not equal to transition state, HA state=" + haConfig.getState() + ", new state=" + transition.getToState());
        }
        return processHAStateChange(haConfig, transition.getToState(), status);
    }

};