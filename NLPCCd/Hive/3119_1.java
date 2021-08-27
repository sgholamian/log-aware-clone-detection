//,temp,sample_3690.java,2,19,temp,sample_3691.java,2,19
//,2
public class xxx {
public void dummy_method(){
String column = ExprNodeDescUtils.extractColName(ctx.parent);
boolean semiJoinAttempted = false;
if (column != null) {
String keyBaseAlias = "";
Table table = ts.getConf().getTableMetadata();
if (table != null && table.isPartitionKey(column)) {
String columnType = table.getPartColByName(column).getType();
String alias = ts.getConf().getAlias();
PrunedPartitionList plist = parseContext.getPrunedPartitions(alias, ts);
if (LOG.isDebugEnabled()) {


log.info("alias");
}
}
}
}

};