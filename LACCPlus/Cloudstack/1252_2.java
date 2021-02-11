//,temp,Upgrade2214to30.java,1012,1063,temp,Upgrade2214to30.java,382,419
//,3
public class xxx {
    private void encryptConfigValues(Connection conn) {
        s_logger.debug("Encrypting Config values");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("select name, value from `cloud`.`configuration` where category in ('Hidden', 'Secure')");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                String value = rs.getString(2);
                if (value == null) {
                    continue;
                }
                String encryptedValue = DBEncryptionUtil.encrypt(value);
                pstmt = conn.prepareStatement("update `cloud`.`configuration` set value=? where name=?");
                pstmt.setBytes(1, encryptedValue.getBytes("UTF-8"));
                pstmt.setString(2, name);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable encrypt configuration values ", e);
        } catch (UnsupportedEncodingException e) {
            throw new CloudRuntimeException("Unable encrypt configuration values ", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                s_logger.info("[ignored]",e);
            }
        }
        s_logger.debug("Done encrypting Config values");
    }

};