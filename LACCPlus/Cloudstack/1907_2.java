//,temp,Upgrade410to420.java,1834,1853,temp,Merovingian2.java,232,250
//,3
public class xxx {
    @Override
    public void cleanupForServer(long msId) {
        s_logger.info("Cleaning up locks for " + msId);
        try {
            synchronized (_concierge.conn()) {
                try(PreparedStatement pstmt = _concierge.conn().prepareStatement(CLEANUP_MGMT_LOCKS_SQL);) {
                    pstmt.setLong(1, msId);
                    int rows = pstmt.executeUpdate();
                    s_logger.info("Released " + rows + " locks for " + msId);
                }catch (Exception e) {
                    s_logger.error("cleanupForServer:Exception:"+e.getMessage());
                    throw new CloudRuntimeException("cleanupForServer:Exception:"+e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            s_logger.error("cleanupForServer:Exception:"+e.getMessage());
            throw new CloudRuntimeException("cleanupForServer:Exception:"+e.getMessage(), e);
        }
    }

};