//,temp,sample_1768.java,2,9,temp,sample_8135.java,2,9
//,2
public class xxx {
private static int executeCleanup(String server, String developerServer, String username) throws HttpException, IOException {
String userId = s_userId.get().toString();
String encodedUserId = URLEncoder.encode(userId, "UTF-8");
String url = server + "?command=listUsers&id=" + encodedUserId;


log.info("cleaning up resources for user with url");
}

};