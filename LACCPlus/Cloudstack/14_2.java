//,temp,Upgrade450to451.java,158,165,temp,Upgrade218to22Premium.java,73,85
//,3
public class xxx {
    private void updateUsageIpAddress(Connection conn) {
        try ( // update id information
            PreparedStatement pstmt =
                conn.prepareStatement("update cloud_usage.usage_ip_address uip set id = "
                    + "(select id from cloud.user_ip_address ip where uip.public_ip_address = ip.public_ip_address and ip.data_center_id = uip.zone_id)");
            ) {
            pstmt.executeUpdate();

            s_logger.debug("Upgraded cloud_usage usage_ip_address with Id");
        } catch (Exception e) {
            throw new CloudRuntimeException("Failed to upgrade usage_ip_address: ", e);
        }
    }

};