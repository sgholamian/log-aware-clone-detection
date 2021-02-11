//,temp,sample_1892.java,2,17,temp,sample_1897.java,2,17
//,3
public class xxx {
public void dummy_method(){
if ("default".equals(strInterface)) {
return Arrays.asList(InetAddress.getByName(cachedHostAddress));
}
NetworkInterface netIf;
try {
netIf = NetworkInterface.getByName(strInterface);
if (netIf == null) {
netIf = getSubinterface(strInterface);
}
} catch (SocketException e) {


log.info("i o error finding interface");
}
}

};