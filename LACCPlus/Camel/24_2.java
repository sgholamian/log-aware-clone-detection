//,temp,CassandraAggregationRepository.java,255,260,temp,CassandraIdempotentRepository.java,169,174
//,2
public class xxx {
    protected void initDeleteStatement() {
        Delete delete = generateDelete(table, pkColumns, true);
        SimpleStatement statement = applyConsistencyLevel(delete.build(), writeConsistencyLevel);
        LOGGER.debug("Generated Delete {}", statement);
        deleteStatement = getSession().prepare(statement);
    }

};