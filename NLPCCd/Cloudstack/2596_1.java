//,temp,sample_7747.java,2,17,temp,sample_7741.java,2,17
//,3
public class xxx {
public void dummy_method(){
final List<InetAddress> addrList = new ArrayList<InetAddress>();
try {
for (final NetworkInterface ifc : IteratorUtil.enumerationAsIterable(NetworkInterface.getNetworkInterfaces())) {
if (ifc.isUp() && !ifc.isVirtual() && ifc.getName().equals(ifName)) {
for (final InetAddress addr : IteratorUtil.enumerationAsIterable(ifc.getInetAddresses())) {
addrList.add(addr);
}
}
}
} catch (final SocketException e) {


log.info("socketexception in getalllocalinetaddresses");
}
}

};