//,temp,Upgrade410to420.java,2380,2406,temp,Merovingian2.java,209,226
//,3
public class xxx {
    protected Map<String, String> isLocked(String key) {
        try (PreparedStatement pstmt = _concierge.conn().prepareStatement(INQUIRE_SQL);){
            pstmt.setString(1, key);
            try(ResultSet rs = pstmt.executeQuery();)
            {
                if (!rs.next()) {
                    return null;
                }
                return toLock(rs);
            }catch (SQLException e) {
                s_logger.error("isLocked:Exception:"+e.getMessage());
                throw new CloudRuntimeException("isLocked:Exception:"+e.getMessage(), e);
            }
        } catch (SQLException e) {
            s_logger.error("isLocked:Exception:"+e.getMessage());
            throw new CloudRuntimeException("isLocked:Exception:"+e.getMessage(), e);
        }
    }

};