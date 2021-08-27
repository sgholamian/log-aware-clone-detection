//,temp,sample_5139.java,2,19,temp,sample_5140.java,2,19
//,3
public class xxx {
public void dummy_method(){
for (String dbName : getMSC().getAllDatabases()) {
List<String> materializedViewNames = getMaterializedViewsForRewriting(dbName);
if (materializedViewNames.isEmpty()) {
continue;
}
List<Table> materializedViewTables = getTableObjects(dbName, materializedViewNames);
Map<String, Materialization> databaseInvalidationInfo = getMSC().getMaterializationsInvalidationInfo(dbName, materializedViewNames);
for (Table materializedViewTable : materializedViewTables) {
Materialization materializationInvalidationInfo = databaseInvalidationInfo.get(materializedViewTable.getTableName());
if (materializationInvalidationInfo == null) {


log.info("materialized view ignored for rewriting as there was no information loaded in the invalidation cache");
}
}
}
}

};