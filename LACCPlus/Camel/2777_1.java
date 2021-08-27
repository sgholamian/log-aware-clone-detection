//,temp,ReplicatedHazelcastAggregationRepository.java,313,319,temp,HazelcastAggregationRepository.java,399,405
//,2
public class xxx {
    @Override
    public void confirm(CamelContext camelContext, String exchangeId) {
        LOG.trace("Confirming an exchange with ID {}.", exchangeId);
        if (useRecovery) {
            replicatedPersistedCache.remove(exchangeId);
        }
    }

};