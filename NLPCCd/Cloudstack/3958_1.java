//,temp,sample_1741.java,2,18,temp,sample_1755.java,2,18
//,2
public class xxx {
public void dummy_method(){
url = developerServer + "?command=deployVirtualMachine&securitygrouplist=" + encodedUsername + "&zoneid=" + encodedZoneId + "&serviceofferingid=" + encodedServiceOfferingId + "&templateid=" + encodedTemplateId + "&apikey=" + encodedApiKey + "&signature=" + encodedSignature;
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