//,temp,Upgrade410to420.java,2279,2308,temp,Upgrade302to40.java,922,947
//,3
public class xxx {
    private void encryptSite2SitePSK(Connection conn) {
        s_logger.debug("Encrypting Site2Site Customer Gateway pre-shared key");
        try (PreparedStatement select_pstmt = conn.prepareStatement("select id, ipsec_psk from `cloud`.`s2s_customer_gateway`");){
            try(ResultSet rs = select_pstmt.executeQuery();)
            {
                while (rs.next()) {
                    long id = rs.getLong(1);
                    String value = rs.getString(2);
                    if (value == null) {
                        continue;
                    }
                    String encryptedValue = DBEncryptionUtil.encrypt(value);
                    try(PreparedStatement update_pstmt = conn.prepareStatement("update `cloud`.`s2s_customer_gateway` set ipsec_psk=? where id=?");) {
                        update_pstmt.setBytes(1, encryptedValue.getBytes("UTF-8"));
                        update_pstmt.setLong(2, id);
                        update_pstmt.executeUpdate();
                    }catch (SQLException e) {
                        throw new CloudRuntimeException("encryptSite2SitePSK:Exception:"+e.getMessage(), e);
                    }
                }
            }catch (SQLException e) {
                throw new CloudRuntimeException("encryptSite2SitePSK:Exception:"+e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to encrypt Site2Site Customer Gateway pre-shared key ", e);
        } catch (UnsupportedEncodingException e) {
            throw new CloudRuntimeException("Unable to encrypt Site2Site Customer Gateway pre-shared key ", e);
        }
        s_logger.debug("Done encrypting Site2Site Customer Gateway pre-shared key");
    }

};