//,temp,sample_8085.java,2,17,temp,sample_8087.java,2,17
//,2
public class xxx {
public void dummy_method(){
method = new GetMethod(url);
responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream is = method.getResponseBodyAsStream();
Map<String, String> networkValues = getSingleValueFromXML(is, new String[] {"id"});
String networkIdStr = networkValues.get("id");
if (networkIdStr != null) {
s_networkId.set(networkIdStr);
}
} else {


log.info("create virtual network failed for account with error code aborting deployment test the command was sent with url");
}
}

};