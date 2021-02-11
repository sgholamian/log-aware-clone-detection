//,temp,Merovingian2.java,313,330,temp,Merovingian2.java,209,226
//,3
public class xxx {
    protected List<Map<String, String>> getLocks(String sql, Long msId) {
        try (PreparedStatement pstmt = _concierge.conn().prepareStatement(sql);)
        {
            if (msId != null) {
                pstmt.setLong(1, msId);
            }
            try(ResultSet rs = pstmt.executeQuery();)
            {
                return toLocks(rs);
            }catch (Exception e) {
                s_logger.error("getLocks:Exception:"+e.getMessage());
                throw new CloudRuntimeException("getLocks:Exception:"+e.getMessage(), e);
            }
       } catch (Exception e) {
            s_logger.error("getLocks:Exception:"+e.getMessage());
            throw new CloudRuntimeException("getLocks:Exception:"+e.getMessage(), e);
        }
    }

};