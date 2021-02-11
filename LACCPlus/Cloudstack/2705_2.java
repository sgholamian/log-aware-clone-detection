//,temp,Upgrade301to302.java,94,158,temp,Upgrade30to301.java,72,107
//,3
public class xxx {
    protected void udpateAccountNetworkResourceCount(Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        long accountId = 0;
        try {
            pstmt = conn.prepareStatement("select id from `cloud`.`account` where removed is null");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                accountId = rs.getLong(1);

                //get networks count for the account
                pstmt =
                    conn.prepareStatement("select count(*) from `cloud`.`networks` n, `cloud`.`account_network_ref` a, `cloud`.`network_offerings` no"
                        + " WHERE n.acl_type='Account' and n.id=a.network_id and a.account_id=? and a.is_owner=1 and no.specify_vlan=false and no.traffic_type='Guest'");
                pstmt.setLong(1, accountId);
                rs1 = pstmt.executeQuery();
                long count = 0;
                while (rs1.next()) {
                    count = rs1.getLong(1);
                }

                pstmt = conn.prepareStatement("insert into `cloud`.`resource_count` (account_id, domain_id, type, count) VALUES (?, null, 'network', ?)");
                pstmt.setLong(1, accountId);
                pstmt.setLong(2, count);
                pstmt.executeUpdate();
                s_logger.debug("Updated network resource count for account id=" + accountId + " to be " + count);
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to update network resource count for account id=" + accountId, e);
        } finally {
            closeAutoCloseable(rs);
            closeAutoCloseable(rs1);
            closeAutoCloseable(pstmt);
        }
    }

};