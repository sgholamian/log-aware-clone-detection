//,temp,sample_8203.java,2,10,temp,sample_1790.java,2,10
//,3
public class xxx {
private static int executeEventsAndBilling(String server, String developerServer) throws HttpException, IOException {
String url = server + "?command=listEvents&page=1&pagesize=100&&account=" + s_account.get();
HttpClient client = new HttpClient();
HttpMethod method = new GetMethod(url);
int responseCode = client.executeMethod(method);


log.info("get events response code");
}

};