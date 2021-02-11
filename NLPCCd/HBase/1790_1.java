//,temp,sample_118.java,2,17,temp,sample_119.java,2,17
//,3
public class xxx {
public void dummy_method(){
org.apache.hadoop.hbase.client.SnapshotDescription snapshotPOJO = null;
if (cpHost != null) {
snapshotPOJO = ProtobufUtil.createSnapshotDesc(snapshot);
cpHost.preSnapshot(snapshotPOJO, desc);
}
TableName snapshotTable = TableName.valueOf(snapshot.getTable());
if (master.getTableStateManager().isTableState(snapshotTable, TableState.State.ENABLED)) {
snapshotEnabledTable(snapshot);
}
else if (master.getTableStateManager().isTableState(snapshotTable, TableState.State.DISABLED)) {


log.info("table is disabled running snapshot entirely on master");
}
}

};