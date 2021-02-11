//,temp,XenServerConnectionPool.java,120,134,temp,XenServerConnectionPool.java,101,110
//,3
public class xxx {
    private void removeConnect(String poolUuid) {
        if (poolUuid == null) {
            return;
        }
        XenServerConnection conn = null;
        synchronized (_conns) {
            conn = _conns.remove(poolUuid);
        }
        if (conn != null) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Remove master connection through " + conn.getIp() + " for pool(" + conn.getPoolUuid() + ")");
            }

        }
    }

};