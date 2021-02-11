//,temp,Upgrade450to451.java,84,102,temp,Upgrade302to40.java,922,947
//,3
public class xxx {
    private void encryptConfig(Connection conn) {
        //Encrypt config params and change category to Hidden
        s_logger.debug("Encrypting Config values");
        try (
                PreparedStatement pstmt = conn.prepareStatement("select name, value from `cloud`.`configuration` where name in ('router.ram.size', 'secondary.storage.vm', 'security.hash.key') and category <> 'Hidden'");
                PreparedStatement pstmt1 = conn.prepareStatement("update `cloud`.`configuration` set value=?, category = 'Hidden' where name=?");
                ResultSet rs = pstmt.executeQuery();
            ) {
            while (rs.next()) {
                String name = rs.getString(1);
                String value = rs.getString(2);
                if (value == null) {
                    continue;
                }
                String encryptedValue = DBEncryptionUtil.encrypt(value);
                pstmt1.setBytes(1, encryptedValue.getBytes("UTF-8"));
                pstmt1.setString(2, name);
                pstmt1.executeUpdate();
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable encrypt configuration values ", e);
        } catch (UnsupportedEncodingException e) {
            throw new CloudRuntimeException("Unable encrypt configuration values ", e);
        }
        s_logger.debug("Done encrypting Config values");
    }

};