//,temp,Upgrade410to420.java,2279,2308,temp,Upgrade421to430.java,94,119
//,3
public class xxx {
    private void encryptImageStoreDetails(Connection conn) {
        s_logger.debug("Encrypting image store details");
        try (
                PreparedStatement selectPstmt = conn.prepareStatement("select id, value from `cloud`.`image_store_details` where name = 'key' or name = 'secretkey'");
                ResultSet rs = selectPstmt.executeQuery();
            ) {
            while (rs.next()) {
                long id = rs.getLong(1);
                String value = rs.getString(2);
                if (value == null) {
                    continue;
                }
                String encryptedValue = DBEncryptionUtil.encrypt(value);
                try (PreparedStatement updatePstmt = conn.prepareStatement("update `cloud`.`image_store_details` set value=? where id=?");) {
                    updatePstmt.setBytes(1, encryptedValue.getBytes("UTF-8"));
                    updatePstmt.setLong(2, id);
                    updatePstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable encrypt image_store_details values ", e);
        } catch (UnsupportedEncodingException e) {
            throw new CloudRuntimeException("Unable encrypt image_store_details values ", e);
        }
        s_logger.debug("Done encrypting image_store_details");
    }

};