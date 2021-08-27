//,temp,sample_322.java,2,16,temp,sample_321.java,2,16
//,3
public class xxx {
public void dummy_method(){
tableName = normalizeIdentifier(tableName);
Query query = queryWrapper.query = pm.newQuery(MPartition.class, "table.tableName == t1 && table.database.name == t2");
query.declareParameters("java.lang.String t1, java.lang.String t2");
query.setOrdering("partitionName ascending");
if (max > 0) {
query.setRange(0, max);
}
mparts = (List<MPartition>) query.execute(tableName, dbName);
pm.retrieveAll(mparts);
success = commitTransaction();


log.info("done retrieving all objects for listmpartitions");
}

};