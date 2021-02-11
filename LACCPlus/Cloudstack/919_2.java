//,temp,Upgrade410to420.java,445,460,temp,Upgrade452to460.java,142,156
//,3
public class xxx {
    private void addIndexForVMInstance(final Connection conn) {
        // Drop index if it exists
        final List<String> indexList = new ArrayList<String>();
        s_logger.debug("Dropping index i_vm_instance__instance_name from vm_instance table if it exists");
        indexList.add("i_vm_instance__instance_name");
        DbUpgradeUtils.dropKeysIfExist(conn, "vm_instance", indexList, false);

        // Now add index
        try (PreparedStatement pstmt = conn.prepareStatement("ALTER TABLE `cloud`.`vm_instance` ADD INDEX `i_vm_instance__instance_name`(`instance_name`)");) {
            pstmt.executeUpdate();
            s_logger.debug("Added index i_vm_instance__instance_name to vm_instance table");
        } catch (final SQLException e) {
            throw new CloudRuntimeException("Unable to add index i_vm_instance__instance_name to vm_instance table for the column instance_name", e);
        }
    }

};