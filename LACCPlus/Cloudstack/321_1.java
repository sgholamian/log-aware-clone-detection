//,temp,Merovingian2.java,355,370,temp,Merovingian2.java,313,330
//,3
public class xxx {
    public List<Map<String, String>> getLocksAcquiredBy(long msId, String threadName) {
        try (PreparedStatement pstmt = _concierge.conn().prepareStatement(SELECT_THREAD_LOCKS_SQL);){
            pstmt.setLong(1, msId);
            pstmt.setString(2, threadName);
            try (ResultSet rs =pstmt.executeQuery();) {
                return toLocks(rs);
            }
            catch (Exception e) {
                s_logger.error("getLocksAcquiredBy:Exception:"+e.getMessage());
                throw new CloudRuntimeException("Can't get locks " + pstmt, e);
            }
        } catch (Exception e) {
            s_logger.error("getLocksAcquiredBy:Exception:"+e.getMessage());
            throw new CloudRuntimeException("getLocksAcquiredBy:Exception:"+e.getMessage(), e);
        }
    }

};