//,temp,sample_1769.java,2,12,temp,sample_1795.java,2,12
//,2
public class xxx {
private static int executeCleanup(String server, String developerServer, String username) throws HttpException, IOException {
String userId = s_userId.get().toString();
String encodedUserId = URLEncoder.encode(userId, "UTF-8");
String url = server + "?command=listUsers&id=" + encodedUserId;
HttpClient client = new HttpClient();
HttpMethod method = new GetMethod(url);
int responseCode = client.executeMethod(method);


log.info("get user response code");
}

};