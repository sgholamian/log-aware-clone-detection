//,temp,sample_8229.java,2,17,temp,sample_8250.java,2,16
//,3
public class xxx {
public void dummy_method(){
if (ipAddressList != null) {
ipAddresses = new String[ipAddressList.size()];
ipAddressList.toArray(ipAddresses);
String ipAddressLogStr = "";
if ((ipAddresses != null) && (ipAddresses.length > 0)) {
ipAddressLogStr = ipAddresses[0];
for (int i = 1; i < ipAddresses.length; i++) {
ipAddressLogStr = ipAddressLogStr + "," + ipAddresses[i];
}
}


log.info("got ip addresses");
}
}

};