//,temp,sample_8157.java,2,18,temp,sample_1761.java,2,18
//,3
public class xxx {
public void dummy_method(){
method = new GetMethod(url);
responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream input = method.getResponseBodyAsStream();
Element el = queryAsyncJobResult(server, input);
Map<String, String> values = getSingleValueFromXML(el, new String[] {"id"});
if (values.get("id") == null) {
return 401;
} else {
String volumeId = values.get("id");


log.info("got volume id");
}
}
}

};