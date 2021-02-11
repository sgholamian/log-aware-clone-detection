//,temp,sample_1783.java,2,18,temp,sample_8188.java,2,18
//,2
public class xxx {
public void dummy_method(){
client = new HttpClient();
method = new GetMethod(url);
responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream input = method.getResponseBodyAsStream();
Element el = queryAsyncJobResult(server, input);
Map<String, String> success = getSingleValueFromXML(el, new String[] {"id"});
if (success.get("id") == null) {
return 401;
} else {


log.info("start vm response code");
}
}
}

};