//,temp,Upgrade2214to30.java,1012,1063,temp,Upgrade304to305.java,86,132
//,3
public class xxx {
    private void updateSystemVms(Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean VMware = false;
        try {
            pstmt = conn.prepareStatement("select distinct(hypervisor_type) from `cloud`.`cluster` where removed is null");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if ("VMware".equals(rs.getString(1))) {
                    VMware = true;
                }
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Error while iterating through list of hypervisors in use", e);
        }
        // Just update the VMware system template. Other hypervisor templates are unchanged from previous 3.0.x versions.
        s_logger.debug("Updating VMware System Vms");
        try {
            //Get 3.0.5 VMware system Vm template Id
            pstmt = conn.prepareStatement("select id from `cloud`.`vm_template` where name = 'systemvm-vmware-3.0.5' and removed is null");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                long templateId = rs.getLong(1);
                rs.close();
                pstmt.close();
                // change template type to SYSTEM
                pstmt = conn.prepareStatement("update `cloud`.`vm_template` set type='SYSTEM' where id = ?");
                pstmt.setLong(1, templateId);
                pstmt.executeUpdate();
                pstmt.close();
                // update templete ID of system Vms
                pstmt = conn.prepareStatement("update `cloud`.`vm_instance` set vm_template_id = ? where type <> 'User' and hypervisor_type = 'VMware'");
                pstmt.setLong(1, templateId);
                pstmt.executeUpdate();
                pstmt.close();
            } else {
                if (VMware) {
                    throw new CloudRuntimeException("3.0.5 VMware SystemVm template not found. Cannot upgrade system Vms");
                } else {
                    s_logger.warn("3.0.5 VMware SystemVm template not found. VMware hypervisor is not used, so not failing upgrade");
                }
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Error while updating VMware systemVm template", e);
        }
        s_logger.debug("Updating System Vm Template IDs Complete");
    }

};