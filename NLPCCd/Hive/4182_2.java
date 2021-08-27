//,temp,sample_5139.java,2,19,temp,sample_5140.java,2,19
//,3
public class xxx {
public void dummy_method(){
List<Table> materializedViewTables = getTableObjects(dbName, materializedViewNames);
Map<String, Materialization> databaseInvalidationInfo = getMSC().getMaterializationsInvalidationInfo(dbName, materializedViewNames);
for (Table materializedViewTable : materializedViewTables) {
Materialization materializationInvalidationInfo = databaseInvalidationInfo.get(materializedViewTable.getTableName());
if (materializationInvalidationInfo == null) {
continue;
}
long invalidationTime = materializationInvalidationInfo.getInvalidationTime();
if (diff == 0L) {
if (invalidationTime != 0L) {


log.info("materialized view ignored for rewriting as its contents are outdated");
}
}
}
}

};