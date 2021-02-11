//,temp,Network.java,158,167,temp,Network.java,147,156
//,3
public class xxx {
    public Network.Interface getBridgeByName(String name)
            throws Ovm3ResourceException {
        if (getNetIface("Name", name) != null
                && getNetIface("Name", name).getIfType().contentEquals(BRIDGE)) {
            return getNetIface("Name", name);
        }
        LOGGER.debug("Unable to find bridge by name: " + name);
        setSuccess(false);
        return null;
    }

};