//,temp,sample_1770.java,2,18,temp,sample_8137.java,2,18
//,2
public class xxx {
public void dummy_method(){
String userId = s_userId.get().toString();
String encodedUserId = URLEncoder.encode(userId, "UTF-8");
String url = server + "?command=listUsers&id=" + encodedUserId;
HttpClient client = new HttpClient();
HttpMethod method = new GetMethod(url);
int responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream is = method.getResponseBodyAsStream();
Map<String, String> userInfo = getSingleValueFromXML(is, new String[] {"username", "id", "account"});
if (!username.equals(userInfo.get("username"))) {


log.info("get user failed to retrieve requested user aborting cleanup test following url was sent");
}
}
}

};