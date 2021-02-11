//,temp,sample_8088.java,2,18,temp,sample_8123.java,2,18
//,3
public class xxx {
public void dummy_method(){
signature = signRequest(requestToSign, s_secretKey.get());
encodedSignature = URLEncoder.encode(signature, "UTF-8");
url = developerServer + "?command=deployVirtualMachine" + "&zoneid=" + encodedZoneId + "&serviceofferingid=" + encodedServiceOfferingId + "&diskofferingid=" + diskOfferingId + "&networkids=" + encodedNetworkIds + "&templateid=" + encodedTemplateId + "&apikey=" + encodedApiKey + "&signature=" + encodedSignature;
method = new GetMethod(url);
responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream input = method.getResponseBodyAsStream();
Element el = queryAsyncJobResult(server, input);
Map<String, String> values = getSingleValueFromXML(el, new String[] {"id", "ipaddress"});
if ((values.get("ipaddress") == null) || (values.get("id") == null)) {


log.info("deploy windows vm response code the command was sent with url");
}
}
}

};