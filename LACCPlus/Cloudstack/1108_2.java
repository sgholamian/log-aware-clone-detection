//,temp,Merovingian2.java,178,207,temp,Merovingian2.java,156,176
//,3
public class xxx {
    protected boolean increment(String key, String threadName, int threadId) {
      try (PreparedStatement pstmt = _concierge.conn().prepareStatement(INCREMENT_SQL);){
            pstmt.setString(1, key);
            pstmt.setLong(2, _msId);
            pstmt.setString(3, threadName);
            pstmt.setInt(4, threadId);
            int rows = pstmt.executeUpdate();
            assert (rows <= 1) : "hmm...non unique key? " + pstmt;
            if (s_logger.isTraceEnabled()) {
                s_logger.trace("lck-" + key + (rows == 1 ? " acquired again" : " failed to acquire again"));
            }
            if (rows == 1) {
                incrCount();
                return true;
            }
            return false;
        } catch (Exception e) {
            s_logger.error("increment:Exception:"+e.getMessage());
            throw new CloudRuntimeException("increment:Exception:"+e.getMessage(), e);
        }
    }

};