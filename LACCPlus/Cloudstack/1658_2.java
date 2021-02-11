//,temp,XenServerConnectionPool.java,120,134,temp,XenServerConnectionPool.java,101,110
//,3
public class xxx {
    private void addConnect(String poolUuid, XenServerConnection conn) {
        if (poolUuid == null)
            return;
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Add master connection through " + conn.getIp() + " for pool(" + conn.getPoolUuid() + ")");
        }
        synchronized (_conns) {
            _conns.put(poolUuid, conn);
        }
    }

};