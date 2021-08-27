//,temp,sample_1626.java,2,8,temp,sample_447.java,2,8
//,3
public class xxx {
protected void initSelectStatement() {
Select select = generateSelect(table, getAllColumns(), pkColumns);
select = applyConsistencyLevel(select, readConsistencyLevel);


log.info("generated select");
}

};