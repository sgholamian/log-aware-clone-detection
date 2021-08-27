//,temp,CassandraAggregationRepository.java,288,291,temp,CassandraIdempotentRepository.java,137,142
//,3
public class xxx {
    protected List<Row> selectKeyIds() {
        LOGGER.debug("Selecting keys {}", getPrefixPKValues());
        return getSession().execute(selectKeyIdStatement.bind(getPrefixPKValues())).all();
    }

};