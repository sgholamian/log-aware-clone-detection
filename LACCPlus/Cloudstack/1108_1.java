//,temp,Merovingian2.java,178,207,temp,Merovingian2.java,156,176
//,3
public class xxx {
    protected boolean doAcquire(String key, String threadName, int threadId) {
        long startTime = InaccurateClock.getTime();
        try(PreparedStatement pstmt = _concierge.conn().prepareStatement(ACQUIRE_SQL);) {
            pstmt.setString(1, key);
            pstmt.setLong(2, _msId);
            pstmt.setString(3, threadName);
            pstmt.setInt(4, threadId);
            pstmt.setString(5, DateUtil.getDateDisplayString(_gmtTimeZone, new Date()));
            try {
                int rows = pstmt.executeUpdate();
                if (rows == 1) {
                    if (s_logger.isTraceEnabled()) {
                        s_logger.trace("Acquired for lck-" + key);
                    }
                    incrCount();
                    return true;
                }
            } catch (SQLException e) {
                if (!(e.getSQLState().equals("23000") && e.getErrorCode() == 1062)) {
                    throw new CloudRuntimeException("Unable to lock " + key + ".  Waited " + (InaccurateClock.getTime() - startTime), e);
                }
            }
        } catch (SQLException e) {
            s_logger.error("doAcquire:Exception:"+e.getMessage());
            throw new CloudRuntimeException("Unable to lock " + key + ".  Waited " + (InaccurateClock.getTime() - startTime), e);
        }

        s_logger.trace("Unable to acquire lck-" + key);
        return false;
    }

};