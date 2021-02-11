//,temp,Upgrade302to303.java,242,272,temp,Upgrade2214to30.java,532,563
//,3
public class xxx {
    private void encryptConfig(Connection conn) {
        //Encrypt config params and change category to Hidden
        s_logger.debug("Encrypting Config values");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt =
                conn.prepareStatement("select name, value from `cloud`.`configuration` where name in ('router.ram.size', 'secondary.storage.vm', 'security.hash.key') and category <> 'Hidden'");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                String value = rs.getString(2);
                if (value == null) {
                    continue;
                }
                String encryptedValue = DBEncryptionUtil.encrypt(value);
                pstmt = conn.prepareStatement("update `cloud`.`configuration` set value=?, category = 'Hidden' where name=?");
                pstmt.setBytes(1, encryptedValue.getBytes("UTF-8"));
                pstmt.setString(2, name);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable encrypt configuration values ", e);
        } catch (UnsupportedEncodingException e) {
            throw new CloudRuntimeException("Unable encrypt configuration values ", e);
        } finally {
            closeAutoCloseable(rs);
            closeAutoCloseable(pstmt);
        }
        s_logger.debug("Done encrypting Config values");
    }

};