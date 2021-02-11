//,temp,Upgrade2214to30.java,532,563,temp,Upgrade2214to30.java,499,530
//,2
public class xxx {
    private void encryptUserCredentials(Connection conn) {
        s_logger.debug("Encrypting user keys");
        List<PreparedStatement> pstmt2Close = new ArrayList<PreparedStatement>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("select id, secret_key from `cloud`.`user`");
            pstmt2Close.add(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                long id = rs.getLong(1);
                String secretKey = rs.getString(2);
                String encryptedSecretKey = DBEncryptionUtil.encrypt(secretKey);
                pstmt = conn.prepareStatement("update `cloud`.`user` set secret_key=? where id=?");
                pstmt2Close.add(pstmt);
                if (encryptedSecretKey == null) {
                    pstmt.setNull(1, Types.VARCHAR);
                } else {
                    pstmt.setBytes(1, encryptedSecretKey.getBytes("UTF-8"));
                }
                pstmt.setLong(2, id);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable encrypt user secret key ", e);
        } catch (UnsupportedEncodingException e) {
            throw new CloudRuntimeException("Unable encrypt user secret key ", e);
        } finally {
            TransactionLegacy.closePstmts(pstmt2Close);
        }
        s_logger.debug("Done encrypting user keys");
    }

};