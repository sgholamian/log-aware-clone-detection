//,temp,sample_5467.java,2,20,temp,sample_5457.java,2,20
//,3
public class xxx {
public void dummy_method(){
Set<Operator<?>> removedOps = new HashSet<>();
for (Entry<String, Long> tablePair : sortedTables) {
String tableName = tablePair.getKey();
for (TableScanOperator discardableTsOp : tableNameToOps.get(tableName)) {
if (removedOps.contains(discardableTsOp)) {
continue;
}
Collection<TableScanOperator> prevTsOps = existingOps.get(tableName);
for (TableScanOperator retainableTsOp : prevTsOps) {
if (removedOps.contains(retainableTsOp)) {


log.info("skip as it has already been removed");
}
}
}
}
}

};