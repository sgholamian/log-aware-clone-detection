//,temp,sample_8189.java,2,17,temp,sample_1785.java,2,17
//,2
public class xxx {
public void dummy_method(){
responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream input = method.getResponseBodyAsStream();
Element el = queryAsyncJobResult(server, input);
Map<String, String> values = getSingleValueFromXML(el, new String[] {"id"});
if (values.get("id") == null) {
return 401;
} else {
}
} else {


log.info("create private template failed with error code following url was sent");
}
}

};