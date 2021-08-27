//,temp,sample_333.java,2,18,temp,sample_332.java,2,16
//,3
public class xxx {
public void dummy_method(){
String queryFilterString = makeQueryFilterString(dbName, null, filter, params);
query = pm.newQuery(MTable.class);
query.declareImports("import java.lang.String");
query.setResult("tableName");
query.setResultClass(java.lang.String.class);
if (maxTables >= 0) {
query.setRange(0, maxTables);
}
if (LOG.isDebugEnabled()) {
for (Entry<String, Object> entry : params.entrySet()) {


log.info("key value class");
}
}
}

};