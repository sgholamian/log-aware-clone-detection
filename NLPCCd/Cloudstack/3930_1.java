//,temp,sample_1727.java,2,17,temp,sample_8074.java,2,17
//,2
public class xxx {
public void dummy_method(){
String returnValue = null;
HttpClient client = new HttpClient();
HttpMethod method = new GetMethod(url);
int responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream is = method.getResponseBodyAsStream();
Map<String, String> requestKeyValues = getSingleValueFromXML(is, new String[] {"apikey", "secretkey"});
s_apiKey.set(requestKeyValues.get("apikey"));
returnValue = requestKeyValues.get("secretkey");
} else {


log.info("registration failed with error code");
}
}

};