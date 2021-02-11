//,temp,Network.java,158,167,temp,Network.java,147,156
//,3
public class xxx {
    public Network.Interface getBridgeByIp(String ip)
            throws Ovm3ResourceException {
        if (getNetIface(ADDRESS, ip) != null
                && getNetIface(ADDRESS, ip).getIfType().contentEquals(BRIDGE)) {
            return getNetIface(ADDRESS, ip);
        }
        LOGGER.debug("Unable to find bridge by ip: " + ip);
        setSuccess(false);
        return null;
    }

};