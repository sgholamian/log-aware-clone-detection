//,temp,sample_1771.java,2,17,temp,sample_8138.java,2,17
//,2
public class xxx {
public void dummy_method(){
HttpClient client = new HttpClient();
HttpMethod method = new GetMethod(url);
int responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream is = method.getResponseBodyAsStream();
Map<String, String> userInfo = getSingleValueFromXML(is, new String[] {"username", "id", "account"});
if (!username.equals(userInfo.get("username"))) {
return -1;
}
} else {


log.info("get user failed with error code following url was sent");
}
}

};