//,temp,Upgrade450to451.java,104,127,temp,Upgrade218to22.java,2331,2361
//,3
public class xxx {
    private void encryptIpSecPresharedKeysOfRemoteAccessVpn(Connection conn) {
        try (
                PreparedStatement selectStatement = conn.prepareStatement("SELECT id, ipsec_psk FROM `cloud`.`remote_access_vpn`");
                ResultSet resultSet = selectStatement.executeQuery();
            ) {
            while (resultSet.next()) {
                Long rowId = resultSet.getLong(1);
                String preSharedKey = resultSet.getString(2);
                try {
                    preSharedKey = DBEncryptionUtil.decrypt(preSharedKey);
                } catch (EncryptionOperationNotPossibleException ignored) {
                    s_logger.debug("The ipsec_psk preshared key id=" + rowId + "in remote_access_vpn is not encrypted, encrypting it.");
                }
                try (PreparedStatement updateStatement = conn.prepareStatement("UPDATE `cloud`.`remote_access_vpn` SET ipsec_psk=? WHERE id=?");) {
                    updateStatement.setString(1, DBEncryptionUtil.encrypt(preSharedKey));
                    updateStatement.setLong(2, rowId);
                    updateStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to update the remote_access_vpn's preshared key ipsec_psk column", e);
        }
        s_logger.debug("Done encrypting remote_access_vpn's ipsec_psk column");
    }

};