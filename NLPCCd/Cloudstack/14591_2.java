//,temp,sample_8101.java,2,17,temp,sample_8106.java,2,17
//,2
public class xxx {
public void dummy_method(){
method = new GetMethod(url);
responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream is = method.getResponseBodyAsStream();
List<String> ipAddressValues = getIPs(is, true);
if ((ipAddressValues != null) && !ipAddressValues.isEmpty()) {
s_linuxIpId.set(ipAddressValues.get(0));
s_linuxIP.set(ipAddressValues.get(1));
}
} else {


log.info("list ip addresses failed with error code following url was sent");
}
}

};