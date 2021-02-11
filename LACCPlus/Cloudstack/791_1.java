//,temp,Upgrade450to451.java,167,174,temp,Upgrade218to22Premium.java,73,85
//,3
public class xxx {
    private void upgradeVMWareLocalStorage(Connection conn) {
        try (PreparedStatement updatePstmt = conn.prepareStatement("UPDATE storage_pool SET pool_type='VMFS',host_address=@newaddress WHERE (@newaddress:=concat('VMFS datastore: ', path)) IS NOT NULL AND scope = 'HOST' AND pool_type = 'LVM' AND id IN (SELECT * FROM (SELECT storage_pool.id FROM storage_pool,cluster WHERE storage_pool.cluster_id = cluster.id AND cluster.hypervisor_type='VMware') AS t);");) {
            updatePstmt.executeUpdate();
            s_logger.debug("Done, upgraded VMWare local storage pool type to VMFS and host_address to the VMFS format");
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to upgrade VMWare local storage pool type", e);
        }
    }

};