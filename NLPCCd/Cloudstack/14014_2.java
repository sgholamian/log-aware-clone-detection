//,temp,sample_8204.java,2,17,temp,sample_1791.java,2,17
//,3
public class xxx {
private static int executeEventsAndBilling(String server, String developerServer) throws HttpException, IOException {
String url = server + "?command=listEvents&page=1&account=" + s_account.get();
HttpClient client = new HttpClient();
HttpMethod method = new GetMethod(url);
int responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream is = method.getResponseBodyAsStream();
Map<String, List<String>> eventDescriptions = getMultipleValuesFromXML(is, new String[] {"description"});
List<String> descriptionText = eventDescriptions.get("description");
if (descriptionText == null) {


log.info("no events retrieved");
}
}
}

};