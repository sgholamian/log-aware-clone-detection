//,temp,JdbcAggregationRepository.java,439,445,temp,JdbcAggregationRepository.java,328,334
//,2
public class xxx {
    @Override
    public Exchange get(final CamelContext camelContext, final String correlationId) {
        final String key = correlationId;
        Exchange result = get(key, getRepositoryName(), camelContext);
        LOG.debug("Getting key {} -> {}", key, result);
        return result;
    }

};