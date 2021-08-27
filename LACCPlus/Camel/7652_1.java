//,temp,JdbcAggregationRepository.java,439,445,temp,JdbcAggregationRepository.java,328,334
//,2
public class xxx {
    @Override
    public Exchange recover(CamelContext camelContext, String exchangeId) {
        final String key = exchangeId;
        Exchange answer = get(key, getRepositoryNameCompleted(), camelContext);
        LOG.debug("Recovering exchangeId {} -> {}", key, answer);
        return answer;
    }

};