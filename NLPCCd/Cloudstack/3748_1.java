//,temp,sample_1756.java,2,18,temp,sample_1742.java,2,18
//,2
public class xxx {
public void dummy_method(){
method = new GetMethod(url);
responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream input = method.getResponseBodyAsStream();
Element el = queryAsyncJobResult(server, input);
Map<String, String> values = getSingleValueFromXML(el, new String[] {"id", "ipaddress"});
if ((values.get("ipaddress") == null) || (values.get("id") == null)) {
return 401;
} else {
long linuxVMId = Long.parseLong(values.get("id"));


log.info("got linux virtual machine id");
}
}
}

};