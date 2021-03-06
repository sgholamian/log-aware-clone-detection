//,temp,Upgrade450to451.java,84,102,temp,Upgrade442to450.java,115,138
//,3
public class xxx {
    private void upgradeMemoryOfInternalLoadBalancervmOffering(Connection conn) {
        int newRamSize = 256; //256MB
        long serviceOfferingId = 0;

        /**
         * Pick first row in service_offering table which has system vm type as internalloadbalancervm. User added offerings would start from 2nd row onwards.
         * We should not update/modify any user-defined offering.
         */

        try (PreparedStatement selectPstmt = conn.prepareStatement("SELECT id FROM `cloud`.`service_offering` WHERE vm_type='internalloadbalancervm'");
             PreparedStatement updatePstmt = conn.prepareStatement("UPDATE `cloud`.`service_offering` SET ram_size=? WHERE id=?");
             ResultSet selectResultSet = selectPstmt.executeQuery()){
            if(selectResultSet.next()) {
                serviceOfferingId = selectResultSet.getLong("id");
            }

            updatePstmt.setInt(1, newRamSize);
            updatePstmt.setLong(2, serviceOfferingId);
            updatePstmt.executeUpdate();
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to upgrade ram_size of service offering for internal loadbalancer vm. ", e);
        }
        s_logger.debug("Done upgrading RAM for service offering of internal loadbalancer vm to " + newRamSize);
    }

};