//,temp,sample_2811.java,2,12,temp,sample_1845.java,2,13
//,3
public class xxx {
public void testCloneSnapshotToSameTable() throws Exception {
SnapshotProtos.SnapshotDescription snapshotDesc = getSnapshot();
final ProcedureExecutor<MasterProcedureEnv> procExec = getMasterProcedureExecutor();
final TableName clonedTableName = TableName.valueOf(snapshotDesc.getTable());
final HTableDescriptor htd = createHTableDescriptor(clonedTableName, CF);
long procId = ProcedureTestingUtility.submitAndWait( procExec, new CloneSnapshotProcedure(procExec.getEnvironment(), htd, snapshotDesc));
Procedure<?> result = procExec.getResult(procId);
assertTrue(result.isFailed());


log.info("clone snapshot failed with exception");
}

};