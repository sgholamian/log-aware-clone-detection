//,temp,sample_3352.java,2,9,temp,sample_3351.java,2,9
//,3
public class xxx {
public Network.Interface getBridgeByName(String name) throws Ovm3ResourceException {
if (getNetIface("Name", name) != null && getNetIface("Name", name).getIfType().contentEquals(BRIDGE)) {
return getNetIface("Name", name);
}


log.info("unable to find bridge by name");
}

};