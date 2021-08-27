//,temp,CassandraAggregationRepository.java,169,174,temp,CassandraIdempotentRepository.java,186,191
//,3
public class xxx {
    private void initInsertStatement() {
        Insert insert = generateInsert(table, getAllColumns(), false, ttl);
        SimpleStatement statement = applyConsistencyLevel(insert.build(), writeConsistencyLevel);
        LOGGER.debug("Generated Insert {}", statement);
        insertStatement = getSession().prepare(statement);
    }

};