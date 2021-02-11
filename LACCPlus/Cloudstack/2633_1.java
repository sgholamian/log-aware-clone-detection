//,temp,Upgrade2214to30.java,532,563,temp,Upgrade2214to30.java,382,419
//,3
public class xxx {
    private void encryptVPNPassword(Connection conn) {
        s_logger.debug("Encrypting vpn_users password");
        List<PreparedStatement> pstmt2Close = new ArrayList<PreparedStatement>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("select id, password from `cloud`.`vpn_users`");
            pstmt2Close.add(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                long id = rs.getLong(1);
                String password = rs.getString(2);
                String encryptedpassword = DBEncryptionUtil.encrypt(password);
                pstmt = conn.prepareStatement("update `cloud`.`vpn_users` set password=? where id=?");
                pstmt2Close.add(pstmt);
                if (encryptedpassword == null) {
                    pstmt.setNull(1, Types.VARCHAR);
                } else {
                    pstmt.setBytes(1, encryptedpassword.getBytes("UTF-8"));
                }
                pstmt.setLong(2, id);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable encrypt vpn_users password ", e);
        } catch (UnsupportedEncodingException e) {
            throw new CloudRuntimeException("Unable encrypt vpn_users password ", e);
        } finally {
            TransactionLegacy.closePstmts(pstmt2Close);
        }
        s_logger.debug("Done encrypting vpn_users password");
    }

};