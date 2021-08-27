//,temp,sample_716.java,2,13,temp,sample_2186.java,2,11
//,3
public class xxx {
private void genAutoColumnStatsGatheringPipeline(QB qb, TableDesc table_desc, Map<String, String> partSpec, Operator curr, boolean isInsertInto) throws SemanticException {
String tableName = table_desc.getTableName();
Table table = null;
try {
table = db.getTable(tableName);
} catch (HiveException e) {
throw new SemanticException(e.getMessage());
}


log.info("generate an operator pipeline to autogather column stats for table in query");
}

};