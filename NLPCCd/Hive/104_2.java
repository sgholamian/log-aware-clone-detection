//,temp,sample_313.java,2,16,temp,sample_314.java,2,16
//,2
public class xxx {
public void dummy_method(){
MTable mtable = getMTable(dbName, tableName);
if( mtable == null ) {
return partNames;
}
Map<String, Object> params = new HashMap<String, Object>();
String queryFilterString = makeQueryFilterString(dbName, mtable, filter, params);
Query query = pm.newQuery( "select partitionName from org.apache.hadoop.hive.metastore.model.MPartition " + "where " + queryFilterString);
if (maxParts >= 0) {
query.setRange(0, maxParts);
}


log.info("parms is");
}

};