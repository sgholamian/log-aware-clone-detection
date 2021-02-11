//,temp,CitrixResourceBase.java,3991,4002,temp,XenServerConnectionPool.java,173,183
//,3
public class xxx {
    private void pbdPlug(final Connection conn, final PBD pbd, final String uuid) {
        try {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Plugging in PBD " + uuid + " for " + _host);
            }
            pbd.plug(conn);
        } catch (final Exception e) {
            final String msg = "PBD " + uuid + " is not attached! and PBD plug failed due to " + e.toString() + ". Please check this PBD in " + _host;
            s_logger.warn(msg, e);
            throw new CloudRuntimeException(msg);
        }
    }

};