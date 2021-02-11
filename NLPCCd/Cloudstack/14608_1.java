//,temp,sample_8118.java,2,18,temp,sample_8166.java,2,18
//,2
public class xxx {
public void dummy_method(){
{
url = server + "?command=listVolumes&virtualMachineId=" + s_linuxVmId.get() + "&type=root";
client = new HttpClient();
method = new GetMethod(url);
responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream is = method.getResponseBodyAsStream();
Map<String, String> success = getSingleValueFromXML(is, new String[] {"id"});
if (success.get("id") == null) {
}


log.info("got rootvolume for linux vm with id id");
}
}
}

};