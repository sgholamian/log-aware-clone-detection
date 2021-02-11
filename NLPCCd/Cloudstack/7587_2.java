//,temp,sample_1739.java,2,17,temp,sample_1785.java,2,17
//,2
public class xxx {
public void dummy_method(){
responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream input = method.getResponseBodyAsStream();
Element el = queryAsyncJobResult(server, input);
Map<String, String> success = getSingleValueFromXML(el, new String[] {"id"});
if (success.get("id") == null) {
return 401;
} else {
}
} else {


log.info("start vm test failed with error code following url was sent");
}
}

};