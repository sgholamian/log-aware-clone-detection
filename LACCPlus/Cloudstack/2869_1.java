//,temp,Upgrade442to450.java,88,113,temp,Upgrade421to430.java,67,92
//,2
public class xxx {
    private void upgradeMemoryOfVirtualRoutervmOffering(Connection conn) {
        int newRamSize = 256; //256MB
        long serviceOfferingId = 0;

        /**
         * Pick first row in service_offering table which has system vm type as domainrouter. User added offerings would start from 2nd row onwards.
         * We should not update/modify any user-defined offering.
         */

        try (
                PreparedStatement selectPstmt = conn.prepareStatement("SELECT id FROM `cloud`.`service_offering` WHERE vm_type='domainrouter'");
                PreparedStatement updatePstmt = conn.prepareStatement("UPDATE `cloud`.`service_offering` SET ram_size=? WHERE id=?");
                ResultSet selectResultSet = selectPstmt.executeQuery();
            ) {
            if(selectResultSet.next()) {
                serviceOfferingId = selectResultSet.getLong("id");
            }

            updatePstmt.setInt(1, newRamSize);
            updatePstmt.setLong(2, serviceOfferingId);
            updatePstmt.executeUpdate();
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to upgrade ram_size of service offering for domain router. ", e);
        }
        s_logger.debug("Done upgrading RAM for service offering of domain router to " + newRamSize);
    }

};