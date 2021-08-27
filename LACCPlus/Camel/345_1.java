//,temp,CassandraIdempotentRepository.java,147,152,temp,CassandraIdempotentRepository.java,130,135
//,3
public class xxx {
    protected void initSelectStatement() {
        Select select = generateSelect(table, pkColumns, pkColumns);
        SimpleStatement statement = applyConsistencyLevel(select.build(), readConsistencyLevel);
        LOGGER.debug("Generated Select {}", statement);
        selectStatement = getSession().prepare(statement);
    }

};