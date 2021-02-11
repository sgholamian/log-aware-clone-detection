//,temp,Upgrade218to22.java,2211,2259,temp,Upgrade218to22.java,2177,2209
//,3
public class xxx {
    private void deleteOrphanedTemplateRef(Connection conn) {
        try (
                PreparedStatement selectStoragePoolRef = conn.prepareStatement("SELECT id, pool_id from template_spool_ref");
                ResultSet rs = selectStoragePoolRef.executeQuery();
            ) {
            if (!rs.next()) {
                s_logger.debug("No records in template_spool_ref, skipping this upgrade part");
                return;
            }
            while (rs.next()) {
                Long id = rs.getLong(1);
                Long poolId = rs.getLong(2);

                try (PreparedStatement selectStoragePool = conn.prepareStatement("SELECT * from storage_pool where id=?");) {
                    selectStoragePool.setLong(1, poolId);
                    try (ResultSet selectedStoragePool = selectStoragePool.executeQuery();) {

                        if (!selectedStoragePool.next()) {
                            s_logger.debug("Orphaned template_spool_ref record is found (storage pool doesn't exist any more0) id=" + id + "; so removing the record");
                            try (PreparedStatement delete = conn.prepareStatement("DELETE FROM template_spool_ref where id=?");) {
                                delete.setLong(1, id);
                                delete.executeUpdate();
                            }
                        }
                    }
                }
            }
            s_logger.debug("Finished deleting orphaned template_spool_ref(s)");
        } catch (Exception e) {
            s_logger.error("Failed to delete orphaned template_spool_ref(s): ", e);
            throw new CloudRuntimeException("Failed to delete orphaned template_spool_ref(s): ", e);
        }
    }

};