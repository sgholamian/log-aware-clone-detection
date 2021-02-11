//,temp,Upgrade302to303.java,242,272,temp,Upgrade2214to30.java,421,451
//,3
public class xxx {
    private void encryptHostDetails(Connection conn) {
        s_logger.debug("Encrypting host details");
        List<PreparedStatement> pstmt2Close = new ArrayList<PreparedStatement>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("select id, value from `cloud`.`host_details` where name = 'password'");
            pstmt2Close.add(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                long id = rs.getLong(1);
                String value = rs.getString(2);
                if (value == null) {
                    continue;
                }
                String encryptedValue = DBEncryptionUtil.encrypt(value);
                pstmt = conn.prepareStatement("update `cloud`.`host_details` set value=? where id=?");
                pstmt2Close.add(pstmt);
                pstmt.setBytes(1, encryptedValue.getBytes("UTF-8"));
                pstmt.setLong(2, id);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable encrypt host_details values ", e);
        } catch (UnsupportedEncodingException e) {
            throw new CloudRuntimeException("Unable encrypt host_details values ", e);
        } finally {
            TransactionLegacy.closePstmts(pstmt2Close);
        }
        s_logger.debug("Done encrypting host details");
    }

};