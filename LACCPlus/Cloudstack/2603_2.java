//,temp,Upgrade450to451.java,104,127,temp,Upgrade302to40.java,949,973
//,3
public class xxx {
    private void encryptClusterDetails(Connection conn) {
        s_logger.debug("Encrypting cluster details");
        try (
                PreparedStatement pstmt = conn.prepareStatement("select id, value from `cloud`.`cluster_details` where name = 'password'");
                PreparedStatement pstmt1 = conn.prepareStatement("update `cloud`.`cluster_details` set value=? where id=?");
                ResultSet rs = pstmt.executeQuery();
            ) {
            while (rs.next()) {
                long id = rs.getLong(1);
                String value = rs.getString(2);
                if (value == null) {
                    continue;
                }
                String encryptedValue = DBEncryptionUtil.encrypt(value);
                pstmt1.setBytes(1, encryptedValue.getBytes("UTF-8"));
                pstmt1.setLong(2, id);
                pstmt1.executeUpdate();
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable encrypt cluster_details values ", e);
        } catch (UnsupportedEncodingException e) {
            throw new CloudRuntimeException("Unable encrypt cluster_details values ", e);
        }
        s_logger.debug("Done encrypting cluster_details");
    }

};