//,temp,Upgrade218to22Premium.java,47,71,temp,Upgrade222to224Premium.java,50,73
//,3
public class xxx {
    private void updateUserStats(Connection conn) {
        try ( // update device_id information
            PreparedStatement pstmt = conn.prepareStatement(
                    "update cloud_usage.user_statistics uus set device_id = (select device_id from cloud.user_statistics us where uus.id = us.id)"
                    );
            ) {
            pstmt.executeUpdate();

            s_logger.debug("Upgraded cloud_usage user_statistics with deviceId");
        } catch (Exception e) {
            throw new CloudRuntimeException("Failed to upgrade user stats: ", e);
        }

        try ( // update host_id information in usage_network
            PreparedStatement pstmt1 = conn.prepareStatement(
                    "update cloud_usage.usage_network un set host_id = "
                    + "(select device_id from cloud_usage.user_statistics us where us.account_id = un.account_id and us.data_center_id = un.zone_id)");
                ) {
            pstmt1.executeUpdate();

            s_logger.debug("Upgraded cloud_usage usage_network with hostId");
        } catch (Exception e) {
            throw new CloudRuntimeException("Failed to upgrade network usage stats: ", e);
        }
    }

};