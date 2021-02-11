//,temp,Upgrade410to420.java,1879,1901,temp,Upgrade410to420.java,1856,1876
//,3
public class xxx {
    private void migrateTemplateHostRef(Connection conn) {
        s_logger.debug("Updating template_store_ref table from template_host_ref table");
        try (PreparedStatement tmplStoreInsert =
                     conn.prepareStatement("INSERT INTO `cloud`.`template_store_ref` (store_id,  template_id, created, last_updated, job_id, download_pct, size, physical_size, download_state, error_str, local_path, install_path, url, destroyed, is_copy, update_count, ref_cnt, store_role, state) select host_id, template_id, created, last_updated, job_id, download_pct, size, physical_size, download_state, error_str, local_path, install_path, url, destroyed, is_copy, 0, 0, 'Image', 'Allocated' from `cloud`.`template_host_ref`");)
        {
            int rowCount = tmplStoreInsert.executeUpdate();
            s_logger.debug("Insert modified " + rowCount + " rows");

            try(PreparedStatement tmplStoreUpdate = conn.prepareStatement("update `cloud`.`template_store_ref` set state = 'Ready' where download_state = 'DOWNLOADED'");) {
                rowCount = tmplStoreUpdate.executeUpdate();
            }catch (SQLException e) {
                s_logger.error("Unable to migrate template_host_ref." + e.getMessage(),e);
                throw new CloudRuntimeException("Unable to migrate template_host_ref." + e.getMessage(), e);
            }
            s_logger.debug("Update modified " + rowCount + " rows");
        } catch (SQLException e) {
            s_logger.error("Unable to migrate template_host_ref." + e.getMessage(),e);
            throw new CloudRuntimeException("Unable to migrate template_host_ref." + e.getMessage(), e);
        }
        s_logger.debug("Completed updating template_store_ref table from template_host_ref table");
    }

};