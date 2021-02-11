//,temp,Upgrade410to420.java,1879,1901,temp,Upgrade410to420.java,1856,1876
//,3
public class xxx {
    private void migrateSnapshotStoreRef(Connection conn) {
        s_logger.debug("Updating snapshot_store_ref table from snapshots table");
        try(PreparedStatement snapshotStoreInsert =
                    conn.prepareStatement("INSERT INTO `cloud`.`snapshot_store_ref` (store_id,  snapshot_id, created, size, parent_snapshot_id, install_path, volume_id, update_count, ref_cnt, store_role, state) select sechost_id, id, created, size, prev_snap_id, CONCAT('snapshots', '/', account_id, '/', volume_id, '/', backup_snap_id), volume_id, 0, 0, 'Image', 'Ready' from `cloud`.`snapshots` where status = 'BackedUp' and hypervisor_type <> 'KVM' and sechost_id is not null and removed is null");
        ) {
            //Update all snapshots except KVM snapshots
            int rowCount = snapshotStoreInsert.executeUpdate();
            s_logger.debug("Inserted " + rowCount + " snapshots into snapshot_store_ref");
            //backsnap_id for KVM snapshots is complate path. CONCAT is not required
            try(PreparedStatement snapshotStoreInsert_2 =
                    conn.prepareStatement("INSERT INTO `cloud`.`snapshot_store_ref` (store_id,  snapshot_id, created, size, parent_snapshot_id, install_path, volume_id, update_count, ref_cnt, store_role, state) select sechost_id, id, created, size, prev_snap_id, backup_snap_id, volume_id, 0, 0, 'Image', 'Ready' from `cloud`.`snapshots` where status = 'BackedUp' and hypervisor_type = 'KVM' and sechost_id is not null and removed is null");) {
                rowCount = snapshotStoreInsert_2.executeUpdate();
                s_logger.debug("Inserted " + rowCount + " KVM snapshots into snapshot_store_ref");
            }catch (SQLException e) {
                s_logger.error("Unable to migrate snapshot_store_ref." + e.getMessage(),e);
                throw new CloudRuntimeException("Unable to migrate snapshot_store_ref." + e.getMessage(),e);
            }
        } catch (SQLException e) {
            s_logger.error("Unable to migrate snapshot_store_ref." + e.getMessage(),e);
            throw new CloudRuntimeException("Unable to migrate snapshot_store_ref." + e.getMessage(),e);
        }
        s_logger.debug("Completed updating snapshot_store_ref table from snapshots table");
    }

};