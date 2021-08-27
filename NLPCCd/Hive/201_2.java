//,temp,sample_337.java,2,16,temp,sample_338.java,2,16
//,2
public class xxx {
public void dummy_method(){
MTable mtable = getMTable(dbName, tableName);
if (mtable == null) {
return partNames;
}
Map<String, Object> params = new HashMap<>();
String queryFilterString = makeQueryFilterString(dbName, mtable, filter, params);
query = pm.newQuery("select partitionName from org.apache.hadoop.hive.metastore.model.MPartition " + "where " + queryFilterString);
if (maxParts >= 0) {
query.setRange(0, maxParts);
}


log.info("parms is");
}

};