//,temp,CassandraAggregationRepository.java,169,174,temp,CassandraIdempotentRepository.java,186,191
//,3
public class xxx {
    protected void initClearStatement() {
        Truncate truncate = generateTruncate(table);
        SimpleStatement statement = applyConsistencyLevel(truncate.build(), writeConsistencyLevel);
        LOGGER.debug("Generated truncate for clear operation {}", statement);
        truncateStatement = getSession().prepare(statement);
    }

};