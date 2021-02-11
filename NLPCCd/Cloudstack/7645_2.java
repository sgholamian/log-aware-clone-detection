//,temp,sample_8124.java,2,18,temp,sample_8089.java,2,18
//,2
public class xxx {
public void dummy_method(){
url = developerServer + "?command=deployVirtualMachine" + "&zoneid=" + encodedZoneId + "&serviceofferingid=" + encodedServiceOfferingId + "&diskofferingid=" + diskOfferingId + "&networkids=" + encodedNetworkIds + "&templateid=" + encodedTemplateId + "&apikey=" + encodedApiKey + "&signature=" + encodedSignature;
method = new GetMethod(url);
responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream input = method.getResponseBodyAsStream();
Element el = queryAsyncJobResult(server, input);
Map<String, String> values = getSingleValueFromXML(el, new String[] {"id", "ipaddress"});
if ((values.get("ipaddress") == null) || (values.get("id") == null)) {
return 401;
} else {


log.info("deploy linux vm response code");
}
}
}

};