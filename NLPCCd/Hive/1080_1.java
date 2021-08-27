//,temp,sample_4742.java,2,17,temp,sample_4743.java,2,18
//,3
public class xxx {
public void dummy_method(){
Task<?> t = createTableTask(tblDesc, x);
table = new Table(tblDesc.getDatabaseName(), tblDesc.getTableName());
Database parentDb = x.getHive().getDatabase(tblDesc.getDatabaseName());
x.getOutputs().add(new WriteEntity(parentDb, WriteEntity.WriteType.DDL_SHARED));
if (isPartitioned(tblDesc)) {
Task<?> ict = createImportCommitTask( tblDesc.getDatabaseName(), tblDesc.getTableName(), txnId, stmtId, x.getConf(), AcidUtils.isInsertOnlyTable(tblDesc.getTblProps()));
for (AddPartitionDesc addPartitionDesc : partitionDescs) {
t.addDependentTask(addSinglePartition(fromURI, fs, tblDesc, table, wh, addPartitionDesc, replicationSpec, x, txnId, stmtId, isSourceMm, ict));
}
} else {


log.info("adding dependent copywork movework for table");
}
}

};