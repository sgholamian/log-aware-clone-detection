//,temp,KubernetesLeadershipController.java,225,240,temp,KubernetesLeadershipController.java,202,219
//,2
public class xxx {
    private void refreshStatusLosingLeadership() {
        // Wait always the same amount of time before giving up the leadership
        long delay = this.lockConfiguration.getLeaseDurationMillis();
        LOG.info("{} Current pod owns the leadership, but it will be lost in {} seconds...", logPrefix(),
                new BigDecimal(delay).divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP));

        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            LOG.warn("Thread interrupted", e);
        }

        LOG.info("{} Current pod is losing leadership now...", logPrefix());
        this.currentState = State.LEADERSHIP_LOST;
        this.serializedExecutor.execute(this::refreshStatus);
    }

};