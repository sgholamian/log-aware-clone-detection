//,temp,sample_1624.java,2,8,temp,sample_452.java,2,8
//,3
public class xxx {
protected void initInsertStatement() {
Insert insert = generateInsert(table, pkColumns, true, ttl);
insert = applyConsistencyLevel(insert, writeConsistencyLevel);


log.info("generated insert");
}

};