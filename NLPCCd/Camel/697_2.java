//,temp,sample_1624.java,2,8,temp,sample_452.java,2,8
//,3
public class xxx {
private void initDeleteStatement() {
Delete delete = generateDelete(table, pkColumns, false);
delete = applyConsistencyLevel(delete, writeConsistencyLevel);


log.info("generated delete");
}

};