//,temp,sample_340.java,2,16,temp,sample_339.java,2,16
//,3
public class xxx {
public void dummy_method(){
query = pm.newQuery("select partitionName from org.apache.hadoop.hive.metastore.model.MPartition " + "where " + queryFilterString);
if (maxParts >= 0) {
query.setRange(0, maxParts);
}
String parameterDeclaration = makeParameterDeclarationStringObj(params);
query.declareParameters(parameterDeclaration);
query.setOrdering("partitionName ascending");
query.setResult("partitionName");
Collection<String> names = (Collection<String>) query.executeWithMap(params);
partNames = new ArrayList<>(names);


log.info("done executing query for listmpartitionnamesbyfilter");
}

};