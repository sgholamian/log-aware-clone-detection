//,temp,sample_5030.java,2,14,temp,sample_5029.java,2,13
//,3
public class xxx {
private void migrateSnapshotStoreRef(Connection conn) {
try(PreparedStatement snapshotStoreInsert = conn.prepareStatement("INSERT INTO `cloud`.`snapshot_store_ref` (store_id,  snapshot_id, created, size, parent_snapshot_id, install_path, volume_id, update_count, ref_cnt, store_role, state) select sechost_id, id, created, size, prev_snap_id, CONCAT('snapshots', '/', account_id, '/', volume_id, '/', backup_snap_id), volume_id, 0, 0, 'Image', 'Ready' from `cloud`.`snapshots` where status = 'BackedUp' and hypervisor_type <> 'KVM' and sechost_id is not null and removed is null");
) {
int rowCount = snapshotStoreInsert.executeUpdate();
try(PreparedStatement snapshotStoreInsert_2 = conn.prepareStatement("INSERT INTO `cloud`.`snapshot_store_ref` (store_id,  snapshot_id, created, size, parent_snapshot_id, install_path, volume_id, update_count, ref_cnt, store_role, state) select sechost_id, id, created, size, prev_snap_id, backup_snap_id, volume_id, 0, 0, 'Image', 'Ready' from `cloud`.`snapshots` where status = 'BackedUp' and hypervisor_type = 'KVM' and sechost_id is not null and removed is null");) {
rowCount = snapshotStoreInsert_2.executeUpdate();


log.info("inserted kvm snapshots into snapshot store ref");
}
}
}

};