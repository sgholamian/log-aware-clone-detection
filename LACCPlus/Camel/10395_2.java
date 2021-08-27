//,temp,KubernetesLeadershipController.java,225,240,temp,KubernetesLeadershipController.java,202,219
//,2
public class xxx {
    private void refreshStatusBecomingLeader() {
        // Wait always the same amount of time before becoming the leader
        // Even if the current pod is already leader, we should let a possible
        // old version of the pod to shut down
        long delay = this.lockConfiguration.getLeaseDurationMillis();
        LOG.info("{} Current pod owns the leadership, but it will be effective in {} seconds...", logPrefix(),
                new BigDecimal(delay).divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP));

        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            LOG.warn("Thread interrupted", e);
        }

        LOG.info("{} Current pod is becoming the new leader now...", logPrefix());
        this.currentState = State.LEADER;
        this.serializedExecutor.execute(this::refreshStatus);
    }

};