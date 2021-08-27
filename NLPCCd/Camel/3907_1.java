//,temp,sample_172.java,2,9,temp,sample_171.java,2,8
//,3
public class xxx {
private StatusLine sendUserMessage(String user, Exchange exchange) throws IOException, InvalidPayloadException {
String urlPath = String.format(getConfig().withAuthToken(HipchatApiConstants.URI_PATH_USER_MESSAGE), user);
Map<String, String> jsonParam = getCommonHttpPostParam(exchange);
StatusLine statusLine = post(urlPath, jsonParam);


log.info("response status for send user message");
}

};