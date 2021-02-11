//,temp,Upgrade450to451.java,158,165,temp,Upgrade218to22Premium.java,73,85
//,3
public class xxx {
    private void updateUserVmDetailsWithNicAdapterType(Connection conn) {
        try (PreparedStatement insertPstmt = conn.prepareStatement("INSERT INTO `cloud`.`user_vm_details`(vm_id,name,value,display) select v.id as vm_id, details.name, details.value, details.display from `cloud`.`vm_instance` as v, `cloud`.`vm_template_details` as details  where v.removed is null and v.vm_template_id=details.template_id and details.name='nicAdapter' and details.template_id in (select id from `cloud`.`vm_template` where hypervisor_type = 'vmware') and v.id not in (select vm_id from `cloud`.`user_vm_details` where name='nicAdapter');");) {
            insertPstmt.executeUpdate();
        } catch (SQLException e) {
            throw new CloudRuntimeException("Failed to update user_vm_details table with nicAdapter entries by copying from vm_template_detail table", e);
        }
        s_logger.debug("Done. Updated user_vm_details table with nicAdapter entries by copying from vm_template_detail table. This affects only VM/templates with hypervisor_type as VMware.");
    }

};