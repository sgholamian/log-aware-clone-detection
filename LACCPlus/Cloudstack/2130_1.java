//,temp,Upgrade2214to30.java,453,497,temp,Upgrade2214to30.java,421,451
//,3
public class xxx {
    private void encryptVNCPassword(Connection conn) {
        s_logger.debug("Encrypting vm_instance vnc_password");
        List<PreparedStatement> pstmt2Close = new ArrayList<PreparedStatement>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            int numRows = 0;
            pstmt = conn.prepareStatement("select count(id) from `cloud`.`vm_instance`");
            pstmt2Close.add(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                numRows = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
            int offset = 0;
            while (offset < numRows) {
                pstmt = conn.prepareStatement("select id, vnc_password from `cloud`.`vm_instance` limit " + offset + ", 500");
                pstmt2Close.add(pstmt);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    long id = rs.getLong(1);
                    String value = rs.getString(2);
                    if (value == null) {
                        continue;
                    }
                    String encryptedValue = DBEncryptionUtil.encrypt(value);
                    pstmt = conn.prepareStatement("update `cloud`.`vm_instance` set vnc_password=? where id=?");
                    pstmt.setBytes(1, encryptedValue.getBytes("UTF-8"));
                    pstmt.setLong(2, id);
                    pstmt.executeUpdate();
                    pstmt.close();
                }
                rs.close();
                offset += 500;
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable encrypt vm_instance vnc_password ", e);
        } catch (UnsupportedEncodingException e) {
            throw new CloudRuntimeException("Unable encrypt vm_instance vnc_password ", e);
        } finally {
            TransactionLegacy.closePstmts(pstmt2Close);
        }
        s_logger.debug("Done encrypting vm_instance vnc_password");
    }

};