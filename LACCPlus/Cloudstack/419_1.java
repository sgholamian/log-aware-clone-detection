//,temp,Upgrade450to451.java,129,156,temp,Upgrade421to430.java,94,119
//,3
public class xxx {
    private void encryptStoragePoolUserInfo(Connection conn) {
        List<PreparedStatement> listOfStatements = new ArrayList<PreparedStatement>();
        try (
                PreparedStatement selectStatement = conn.prepareStatement("SELECT id, user_info FROM `cloud`.`storage_pool` WHERE user_info IS NOT NULL");
                ResultSet resultSet = selectStatement.executeQuery();
            ) {
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String userInfo = resultSet.getString(2);
                String encryptedUserInfo = DBEncryptionUtil.encrypt(userInfo);
                try (PreparedStatement preparedStatement = conn.prepareStatement("UPDATE `cloud`.`storage_pool` SET user_info=? WHERE id=?");) {
                    listOfStatements.add(preparedStatement);
                    if (encryptedUserInfo == null)
                        preparedStatement.setNull(1, 12);
                    else {
                        preparedStatement.setBytes(1, encryptedUserInfo.getBytes("UTF-8"));
                    }
                    preparedStatement.setLong(2, id);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable encrypt storage pool user info ", e);
        } catch (UnsupportedEncodingException e) {
            throw new CloudRuntimeException("Unable encrypt storage pool user info ", e);
        }
        s_logger.debug("Done encrypting storage_pool's user_info column");
    }

};