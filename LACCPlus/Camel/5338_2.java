//,temp,CassandraAggregationRepository.java,226,232,temp,CassandraAggregationRepository.java,196,201
//,3
public class xxx {
    protected void initSelectStatement() {
        Select select = generateSelect(table, getAllColumns(), pkColumns);
        SimpleStatement statement = applyConsistencyLevel(select.build(), readConsistencyLevel);
        LOGGER.debug("Generated Select {}", statement);
        selectStatement = getSession().prepare(statement);
    }

};