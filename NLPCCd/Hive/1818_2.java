//,temp,sample_333.java,2,18,temp,sample_332.java,2,16
//,3
public class xxx {
public void dummy_method(){
dbName = normalizeIdentifier(dbName);
Map<String, Object> params = new HashMap<>();
String queryFilterString = makeQueryFilterString(dbName, null, filter, params);
query = pm.newQuery(MTable.class);
query.declareImports("import java.lang.String");
query.setResult("tableName");
query.setResultClass(java.lang.String.class);
if (maxTables >= 0) {
query.setRange(0, maxTables);
}


log.info("filter specified is jdoql filter is");
}

};