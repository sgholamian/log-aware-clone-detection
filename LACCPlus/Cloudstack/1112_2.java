//,temp,Upgrade302to40.java,549,581,temp,Upgrade224to225.java,82,125
//,3
public class xxx {
    private void createSecurityGroups(Connection conn) {
        s_logger.debug("Creating missing default security group as a part of 224-225 upgrade");
        try {
            List<Long> accounts = new ArrayList<Long>();
            PreparedStatement pstmt = conn.prepareStatement("SELECT id FROM account WHERE removed IS NULL and id != 1");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                accounts.add(rs.getLong(1));
            }

            for (Long accountId : accounts) {
                // get default security group
                pstmt = conn.prepareStatement("SELECT * FROM security_group WHERE name='default' and account_id=?");
                pstmt.setLong(1, accountId);
                rs = pstmt.executeQuery();
                if (!rs.next()) {
                    s_logger.debug("Default security group is missing for account id=" + accountId + " so adding it");

                    // get accountName/domainId information

                    pstmt = conn.prepareStatement("SELECT account_name, domain_id FROM account WHERE id=?");
                    pstmt.setLong(1, accountId);
                    ResultSet rs1 = pstmt.executeQuery();
                    if (!rs1.next()) {
                        throw new CloudRuntimeException("Unable to create default security group for account id=" + accountId +
                            ": unable to get accountName/domainId info");
                    }
                    String accountName = rs1.getString(1);
                    Long domainId = rs1.getLong(2);

                    pstmt =
                        conn.prepareStatement("INSERT INTO `cloud`.`security_group` (name, description, account_name, account_id, domain_id) VALUES ('default', 'Default Security Group', ?, ?, ?)");
                    pstmt.setString(1, accountName);
                    pstmt.setLong(2, accountId);
                    pstmt.setLong(3, domainId);
                    pstmt.executeUpdate();
                }
                rs.close();
                pstmt.close();
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to create default security groups for existing accounts due to", e);
        }
    }

};