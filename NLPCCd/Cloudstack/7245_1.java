//,temp,sample_1780.java,2,17,temp,sample_1804.java,2,17
//,3
public class xxx {
public void dummy_method(){
encodedSignature = URLEncoder.encode(signature, "UTF-8");
url = developerServer + "?command=stopVirtualMachine&id=" + s_linuxVmId.get() + "&apikey=" + encodedApiKey + "&signature=" + encodedSignature;
client = new HttpClient();
method = new GetMethod(url);
responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream input = method.getResponseBodyAsStream();
Element el = queryAsyncJobResult(server, input);
Map<String, String> success = getSingleValueFromXML(el, new String[] {"success"});
} else {


log.info("stop vm test failed with error code following url was sent");
}
}

};