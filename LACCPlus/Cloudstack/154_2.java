//,temp,Upgrade218to22Premium.java,47,71,temp,Upgrade222to224Premium.java,50,73
//,3
public class xxx {
    private void updateUserStats(Connection conn) {
        try (   // update network_id information
                PreparedStatement pstmt = conn.prepareStatement(
                    "update cloud_usage.user_statistics uus, cloud.user_statistics us set uus.network_id = us.network_id where uus.id = us.id"
                    );
            ) {

            pstmt.executeUpdate();
            s_logger.debug("Upgraded cloud_usage user_statistics with networkId");
        } catch (Exception e) {
            throw new CloudRuntimeException("Failed to upgrade user stats: ", e);
        }

        try (   // update network_id information in usage_network
                PreparedStatement pstmt1 =
                    conn.prepareStatement("update cloud_usage.usage_network un, cloud_usage.user_statistics us set un.network_id = "
                    + "us.network_id where us.account_id = un.account_id and us.data_center_id = un.zone_id and us.device_id = un.host_id");
            ) {
            pstmt1.executeUpdate();
            s_logger.debug("Upgraded cloud_usage usage_network with networkId");
        } catch (Exception e) {
            throw new CloudRuntimeException("Failed to upgrade user stats: ", e);
        }
    }

};