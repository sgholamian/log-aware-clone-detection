//,temp,CassandraIdempotentRepository.java,147,152,temp,CassandraIdempotentRepository.java,130,135
//,3
public class xxx {
    protected void initInsertStatement() {
        Insert insert = generateInsert(table, pkColumns, true, ttl);
        SimpleStatement statement = applyConsistencyLevel(insert.build(), writeConsistencyLevel);
        LOGGER.debug("Generated Insert {}", statement);
        insertStatement = getSession().prepare(statement);
    }

};