//,temp,Upgrade410to420.java,1879,1901,temp,Upgrade410to420.java,1834,1853
//,3
public class xxx {
    private void migrateVolumeHostRef(Connection conn) {
        s_logger.debug("Updating volume_store_ref table from volume_host_ref table");
        try(PreparedStatement volStoreInsert =
                    conn.prepareStatement("INSERT INTO `cloud`.`volume_store_ref` (store_id,  volume_id, zone_id, created, last_updated, job_id, download_pct, size, physical_size, download_state, checksum, error_str, local_path, install_path, url, destroyed, update_count, ref_cnt, state) select host_id, volume_id, zone_id, created, last_updated, job_id, download_pct, size, physical_size, download_state, checksum, error_str, local_path, install_path, url, destroyed, 0, 0, 'Allocated' from `cloud`.`volume_host_ref`");)
        {
            int rowCount = volStoreInsert.executeUpdate();
            s_logger.debug("Insert modified " + rowCount + " rows");
            try(PreparedStatement volStoreUpdate = conn.prepareStatement("update `cloud`.`volume_store_ref` set state = 'Ready' where download_state = 'DOWNLOADED'");) {
                rowCount = volStoreUpdate.executeUpdate();
                s_logger.debug("Update modified " + rowCount + " rows");
            }catch (SQLException e) {
                s_logger.error("Unable to migrate volume_host_ref." + e.getMessage(),e);
                throw new CloudRuntimeException("Unable to migrate volume_host_ref." + e.getMessage(),e);
            }
        } catch (SQLException e) {
            s_logger.error("Unable to migrate volume_host_ref." + e.getMessage(),e);
            throw new CloudRuntimeException("Unable to migrate volume_host_ref." + e.getMessage(),e);
        }
        s_logger.debug("Completed updating volume_store_ref table from volume_host_ref table");
    }

};