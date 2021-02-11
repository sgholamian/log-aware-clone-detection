//,temp,Upgrade410to420.java,2042,2089,temp,Upgrade410to420.java,1993,2039
//,3
public class xxx {
    private void migrateSnapshotS3Ref(Connection conn, Map<Long, Long> s3StoreMap) {
        s_logger.debug("Updating snapshot_store_ref table from snapshots table for s3");
        try(PreparedStatement snapshotStoreInsert =
                    conn.prepareStatement("INSERT INTO `cloud`.`snapshot_store_ref` (store_id,  snapshot_id, created, size, parent_snapshot_id, install_path, volume_id, update_count, ref_cnt, store_role, state) values(?, ?, ?, ?, ?, ?, ?, 0, 0, 'Image', 'Ready')");
        ) {
            try(PreparedStatement s3Query =
                    conn.prepareStatement("select s3_id, id, created, size, prev_snap_id, CONCAT('snapshots', '/', account_id, '/', volume_id, '/', backup_snap_id), volume_id, 0, 0, 'Image', 'Ready' from `cloud`.`snapshots` where status = 'BackedUp' and hypervisor_type <> 'KVM' and s3_id is not null and removed is null");) {
                try(ResultSet rs = s3Query.executeQuery();) {
                    while (rs.next()) {
                        Long s3_id = rs.getLong("s3_id");
                        Long snapshot_id = rs.getLong("id");
                        Date s3_created = rs.getDate("created");
                        Long s3_size = rs.getObject("size") != null ? rs.getLong("size") : null;
                        Long s3_prev_id = rs.getObject("prev_snap_id") != null ? rs.getLong("prev_snap_id") : null;
                        String install_path = rs.getString(6);
                        Long s3_vol_id = rs.getLong("volume_id");

                        snapshotStoreInsert.setLong(1, s3StoreMap.get(s3_id));
                        snapshotStoreInsert.setLong(2, snapshot_id);
                        snapshotStoreInsert.setDate(3, s3_created);
                        if (s3_size != null) {
                            snapshotStoreInsert.setLong(4, s3_size);
                        } else {
                            snapshotStoreInsert.setNull(4, Types.BIGINT);
                        }
                        if (s3_prev_id != null) {
                            snapshotStoreInsert.setLong(5, s3_prev_id);
                        } else {
                            snapshotStoreInsert.setNull(5, Types.BIGINT);
                        }
                        snapshotStoreInsert.setString(6, install_path);
                        snapshotStoreInsert.setLong(7, s3_vol_id);
                        snapshotStoreInsert.executeUpdate();
                    }
                }catch (SQLException e) {
                    s_logger.error("migrateSnapshotS3Ref:Exception:"+e.getMessage(),e);
                    throw new CloudRuntimeException("migrateSnapshotS3Ref:Exception:"+e.getMessage(),e);
                }
            }catch (SQLException e) {
                s_logger.error("migrateSnapshotS3Ref:Exception:"+e.getMessage(),e);
                throw new CloudRuntimeException("migrateSnapshotS3Ref:Exception:"+e.getMessage(),e);
            }
        } catch (SQLException e) {
            s_logger.error("Unable to migrate s3 backedup snapshots to snapshot_store_ref." + e.getMessage());
            throw new CloudRuntimeException("Unable to migrate s3 backedup snapshots to snapshot_store_ref." + e.getMessage(), e);
        }
        s_logger.debug("Completed updating snapshot_store_ref table from s3 snapshots entries");
    }

};