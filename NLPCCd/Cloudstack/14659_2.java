//,temp,sample_1745.java,2,19,temp,sample_1759.java,2,19
//,2
public class xxx {
public void dummy_method(){
{
url = server + "?command=createVolume&diskofferingid=" + diskOfferingId1 + "&zoneid=" + zoneId + "&name=newvolume1&account=" + s_account.get() + "&domainid=1";
client = new HttpClient();
method = new GetMethod(url);
responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream input = method.getResponseBodyAsStream();
Element el = queryAsyncJobResult(server, input);
Map<String, String> values = getSingleValueFromXML(el, new String[] {"id"});
if (values.get("id") == null) {


log.info("create volume response code");
}
}
}
}

};