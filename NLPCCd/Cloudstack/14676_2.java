//,temp,sample_8175.java,2,17,temp,sample_8171.java,2,17
//,2
public class xxx {
public void dummy_method(){
if (responseCode == 200) {
InputStream input = method.getResponseBodyAsStream();
Element el = queryAsyncJobResult(server, input);
Map<String, String> values = getSingleValueFromXML(el, new String[] {"id"});
if (values.get("id") == null) {
return 401;
} else {
s_snapshot.set(values.get("id"));
}
} else {


log.info("create snapshot failed with error code following url was sent");
}
}

};