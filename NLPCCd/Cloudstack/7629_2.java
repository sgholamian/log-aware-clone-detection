//,temp,sample_8167.java,2,17,temp,sample_8119.java,2,17
//,2
public class xxx {
public void dummy_method(){
client = new HttpClient();
method = new GetMethod(url);
responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream is = method.getResponseBodyAsStream();
Map<String, String> success = getSingleValueFromXML(is, new String[] {"id"});
if (success.get("id") == null) {
}
s_rootVolume.set(success.get("id"));
} else {


log.info("list volumes for linux vm failed with error code following url was sent");
}
}

};