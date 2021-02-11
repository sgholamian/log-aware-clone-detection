//,temp,ClusteredAgentManagerImpl.java,1218,1228,temp,ConsoleProxyResource.java,426,441
//,3
public class xxx {
    public void ensureRoute(String address) {
        if (_localgw != null) {
            if (s_logger.isDebugEnabled())
                s_logger.debug("Ensure route for " + address + " via " + _localgw);

            // this method won't be called in high frequency, serialize access
            // to script execution
            synchronized (this) {
                try {
                    addRouteToInternalIpOrCidr(_localgw, _eth1ip, _eth1mask, address);
                } catch (Throwable e) {
                    s_logger.warn("Unexpected exception while adding internal route to " + address, e);
                }
            }
        }
    }

};