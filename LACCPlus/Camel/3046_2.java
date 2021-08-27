//,temp,CassandraAggregationRepository.java,288,291,temp,CassandraIdempotentRepository.java,137,142
//,3
public class xxx {
    @Override
    public boolean add(String key) {
        Object[] idValues = getPKValues(key);
        LOGGER.debug("Inserting key {}", (Object) idValues);
        return isApplied(getSession().execute(insertStatement.bind(idValues)));
    }

};