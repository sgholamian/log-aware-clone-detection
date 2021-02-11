//,temp,sample_8105.java,2,18,temp,sample_8104.java,2,18
//,2
public class xxx {
public void dummy_method(){
url = developerServer + "?command=listPublicIpAddresses&apikey=" + encodedApiKey + "&signature=" + encodedSignature;
client = new HttpClient();
method = new GetMethod(url);
responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream is = method.getResponseBodyAsStream();
List<String> ipAddressValues = getIPs(is, true);
if ((ipAddressValues != null) && !ipAddressValues.isEmpty()) {
s_linuxIpId.set(ipAddressValues.get(0));
s_linuxIP.set(ipAddressValues.get(1));


log.info("for linux using sourcenat ip address id");
}
}
}

};