//,temp,sample_8182.java,2,17,temp,sample_8185.java,2,17
//,2
public class xxx {
public void dummy_method(){
encodedSignature = URLEncoder.encode(signature, "UTF-8");
url = developerServer + "?command=rebootVirtualMachine&id=" + s_windowsVmId.get() + "&apikey=" + encodedApiKey + "&signature=" + encodedSignature;
client = new HttpClient();
method = new GetMethod(url);
responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream input = method.getResponseBodyAsStream();
Element el = queryAsyncJobResult(server, input);
Map<String, String> success = getSingleValueFromXML(el, new String[] {"success"});
} else {


log.info("reboot windows vm test failed with error code following url was sent");
}
}

};