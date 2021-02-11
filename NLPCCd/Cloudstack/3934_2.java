//,temp,sample_8100.java,2,18,temp,sample_8099.java,2,18
//,2
public class xxx {
public void dummy_method(){
url = developerServer + "?command=listPublicIpAddresses&apikey=" + encodedApiKey + "&id=" + encodedPublicIpId + "&signature=" + encodedSignature;
client = new HttpClient();
method = new GetMethod(url);
responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream is = method.getResponseBodyAsStream();
List<String> ipAddressValues = getIPs(is, false);
if ((ipAddressValues != null) && !ipAddressValues.isEmpty()) {
s_windowsIpId.set(ipAddressValues.get(0));
s_windowsIP.set(ipAddressValues.get(1));


log.info("for windows using non sourcenat ip address id");
}
}
}

};