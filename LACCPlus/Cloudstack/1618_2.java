//,temp,Upgrade222to224.java,434,450,temp,Upgrade224to225.java,339,353
//,3
public class xxx {
    private void addMissingOvsAccount(Connection conn) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * from ovs_tunnel_account");
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                s_logger.debug("Adding missing ovs tunnel account");
                pstmt =
                    conn.prepareStatement("INSERT INTO `cloud`.`ovs_tunnel_account` (`from`, `to`, `account`, `key`, `port_name`, `state`) VALUES (0, 0, 0, 0, 'lock', 'SUCCESS')");
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            s_logger.error("Unable to add missing ovs tunnel account due to ", e);
            throw new CloudRuntimeException("Unable to add missign ovs tunnel account due to ", e);
        }
    }

};