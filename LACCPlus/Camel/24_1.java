//,temp,CassandraAggregationRepository.java,255,260,temp,CassandraIdempotentRepository.java,169,174
//,2
public class xxx {
    private void initDeleteStatement() {
        Delete delete = generateDelete(table, pkColumns, false);
        SimpleStatement statement = applyConsistencyLevel(delete.build(), writeConsistencyLevel);
        LOGGER.debug("Generated Delete {}", statement);
        deleteStatement = getSession().prepare(statement);
    }

};