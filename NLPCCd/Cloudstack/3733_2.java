//,temp,sample_1765.java,2,19,temp,sample_1751.java,2,19
//,2
public class xxx {
public void dummy_method(){
{
url = server + "?command=attachVolume&id=" + s_newVolume.get() + "&virtualmachineid=" + s_linuxVmId.get();
client = new HttpClient();
method = new GetMethod(url);
responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream input = method.getResponseBodyAsStream();
Element el = queryAsyncJobResult(server, input);
Map<String, String> values = getSingleValueFromXML(el, new String[] {"id"});
if (values.get("id") == null) {


log.info("attach volume response code");
}
}
}
}

};