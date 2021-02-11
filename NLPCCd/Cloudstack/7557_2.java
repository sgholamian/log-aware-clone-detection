//,temp,sample_8156.java,2,18,temp,sample_1760.java,2,18
//,2
public class xxx {
public void dummy_method(){
client = new HttpClient();
method = new GetMethod(url);
responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream input = method.getResponseBodyAsStream();
Element el = queryAsyncJobResult(server, input);
Map<String, String> values = getSingleValueFromXML(el, new String[] {"id"});
if (values.get("id") == null) {
return 401;
} else {


log.info("create volume response code");
}
}
}

};